package com.cosmetics.api.controller;

import com.cosmetics.domain.goods.dto.GoodsManagement;
import com.cosmetics.domain.goods.dto.GoodsManagementRequest;
import com.cosmetics.domain.goods.dto.GoodsManagementResponse;
import com.cosmetics.domain.goods.service.GoodsService;
import com.cosmetics.domain.sms.service.SmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/v1/goods")
@RequiredArgsConstructor
@Slf4j
public class GoodsController {

    private final GoodsService goodsService;
    private final SmsService smsService;

    @GetMapping(value = "/{goodsNo}")
    public CompletableFuture<GoodsManagementResponse> findGoods(@PathVariable Long goodsNo) {
        return goodsService.findByGoodsNo(goodsNo).thenApplyAsync(goodsManagement -> {
            return GoodsManagementResponse.fromDto(goodsManagement);
        }).thenApplyAsync(
                (GoodsManagementResponse response) -> {
                    log.error("findGoods thenApplayAsync= {}", Thread.currentThread().getName());
                    response.updateSuccess("0000", "조회성공");
                    return response;
                }
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<GoodsManagementResponse> registerGoods(@RequestBody @Valid GoodsManagementRequest goodsManagementRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<GoodsManagementResponse>  goodsResponseFuture = goodsService.save(goodsManagementRequest.toDto()).thenApplyAsync(
                (goodsManagement) -> {
                    log.error("saveAfter goodsName = {}, goodsNo = {}", goodsManagement.getGoodsNm(), goodsManagement.getGoodsNo());
                    log.error("registerGoodsThread thenApplyAsync = {}", Thread.currentThread().getName()); //ForkJoinPool.commonPool-worker-1
                    GoodsManagementResponse response = GoodsManagementResponse.fromDto(goodsManagement);
                    response.updateSuccess("0000", "등록성공");
                    return response;
                }
        ).thenApplyAsync(
                (response) ->  {
                    if("0000".equals(response.getResultCode())){
                        smsService.smsMessage(response.getGoodsNo()); //Cosmetics-Thread-Pool1
                    }
                    return response;
                }
        );
        return goodsResponseFuture;
    }


    @DeleteMapping(value = "/{goodsNo}")
    public GoodsManagementResponse deleteGoods(@PathVariable Long goodsNo) {
        log.error("deleteGoods : {}", goodsNo);
        GoodsManagement goodsManagement = goodsService.deleteByGoodsNo(goodsNo);
        //dto -> responseDto
        GoodsManagementResponse responseGoodsManagement = GoodsManagementResponse.fromDto(goodsManagement);
        responseGoodsManagement.updateSuccess("0000", "삭제성공");
        return responseGoodsManagement;
    }

}
