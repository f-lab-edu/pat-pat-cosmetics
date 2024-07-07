package com.cosmetics.domain.goods.dto;

import com.cosmetics.domain.goods.dto.item.GoodsItemManagement;
import com.cosmetics.domain.goods.dto.item.GoodsItemManagementResponse;
import com.cosmetics.domain.response.ResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsManagementResponse extends ResponseDto {
    private Long goodsNo;
    private String category;
    private String goodsNm;
    private long salePrice;
    private long marketPrice;
    private long supplyPrice;
    private Long vendorId;
    private int stockQty;
    private String brandNm;
    private String saleStartDtime;
    private String saleEndDtime;
    private List<GoodsItemManagementResponse> items;
    private String image;
    private String addImage;

    @Builder
    public GoodsManagementResponse(Long goodsNo,
                                   String category,
                                   String goodsNm,
                                   long salePrice,
                                   long marketPrice,
                                   long supplyPrice,
                                   Long vendorId,
                                   int stockQty,
                                   String brandNm,
                                   String saleStartDtime,
                                   String saleEndDtime,
                                   List<GoodsItemManagementResponse> items,
                                   String image,
                                   String addImage,
                                   String resultCode,
                                   String resultMsg) {
        super(resultCode, resultMsg);
        this.goodsNo = goodsNo;
        this.category = category;
        this.goodsNm = goodsNm;
        this.salePrice = salePrice;
        this.marketPrice = marketPrice;
        this.supplyPrice = supplyPrice;
        this.vendorId = vendorId;
        this.stockQty = stockQty;
        this.brandNm = brandNm;
        this.saleStartDtime = saleStartDtime;
        this.saleEndDtime = saleEndDtime;
        this.items = items;
        this.image = image;
        this.addImage = addImage;
    }

    public static GoodsManagementResponse fromDto(GoodsManagement goodsManagent) {
        return GoodsManagementResponse.builder()
                .goodsNo(goodsManagent.getGoodsNo())
                .category(goodsManagent.getCategory())
                .goodsNm(goodsManagent.getGoodsNm())
                .salePrice(goodsManagent.getSalePrice())
                .marketPrice(goodsManagent.getMarketPrice())
                .supplyPrice(goodsManagent.getSupplyPrice())
                .vendorId(goodsManagent.getVendorId())
                .stockQty(goodsManagent.getStockQty())
                .brandNm(goodsManagent.getBrandNm())
                .saleStartDtime(goodsManagent.getSaleStartDtime())
                .saleEndDtime(goodsManagent.getSaleEndDtime())
                .items(toGoodsManagementList(goodsManagent.getItems()))
                .image(goodsManagent.getImage())
                .addImage(goodsManagent.getAddImage())
                .build();
    }

    public static List<GoodsItemManagementResponse> toGoodsManagementList(List<GoodsItemManagement> goodsItemManagement) {
        return goodsItemManagement.stream()
                .map(GoodsItemManagementResponse::new)
                .collect(Collectors.toList());
    }


}
