package com.cosmetics.domain.goods.service;

import com.cosmetics.domain.goods.dto.GoodsManagement;
import com.cosmetics.domain.goods.entity.GoodsManagementEntity;
import com.cosmetics.domain.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    @Transactional
    public CompletableFuture<GoodsManagement> findByGoodsNo(Long goodsNo) {
        return CompletableFuture.supplyAsync(() -> {
            log.error("GoodsService Thread = {}", Thread.currentThread().getName());
            GoodsManagementEntity goodsManagementEntity =  goodsRepository.findByGoodsNo(goodsNo).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
            return GoodsManagement.fromEntity(goodsManagementEntity);
        });
    }

    @Transactional
    public CompletableFuture<GoodsManagement> save(GoodsManagement goodsManagement) {
        CompletableFuture<GoodsManagement> goodsManageFuture = CompletableFuture.supplyAsync(() -> {
            log.error("GoodsService Thread = {}", Thread.currentThread().getName());
            GoodsManagementEntity resultGoodsManagementEntity = goodsRepository.save(goodsManagement.toEntity());
            return GoodsManagement.fromEntity(resultGoodsManagementEntity);
        });
        return goodsManageFuture;
    }

    @Transactional
    public GoodsManagement deleteByGoodsNo(Long goodsNo) {
        GoodsManagementEntity goodsManagementEntity = goodsRepository.findByGoodsNo(goodsNo).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        long deleteCnt = goodsRepository.deleteByGoodsNo(goodsNo);
        //entity -> dto
        return GoodsManagement.fromEntity(goodsManagementEntity);
    }
}
