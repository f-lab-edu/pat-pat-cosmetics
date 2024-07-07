package com.cosmetics.domain.goods.dto;

import com.cosmetics.domain.goods.dto.item.GoodsItemManagement;
import com.cosmetics.domain.goods.entity.GoodsItemManagementEntity;
import com.cosmetics.domain.goods.entity.GoodsManagementEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GoodsManagement {
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

    private List<GoodsItemManagement> items;
    private String image;
    private String addImage;

    @Builder
    public GoodsManagement(Long goodsNo,
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
                           List<GoodsItemManagement> items,
                           String image,
                           String addImage) {
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

    public static GoodsManagement fromEntity(GoodsManagementEntity goodsManagementEntity) {
        return GoodsManagement.builder()
                .goodsNo(goodsManagementEntity.getGoodsNo())
                .goodsNm(goodsManagementEntity.getGoodsNm())
                .category(goodsManagementEntity.getCategory())
                .salePrice(goodsManagementEntity.getSalePrice())
                .marketPrice(goodsManagementEntity.getMarketPrice())
                .supplyPrice(goodsManagementEntity.getSupplyPrice())
                .vendorId(goodsManagementEntity.getVendorId())
                .stockQty(goodsManagementEntity.getStockQty())
                .brandNm(goodsManagementEntity.getBrandNm())
                .saleStartDtime(goodsManagementEntity.getSaleStartDtime())
                .saleEndDtime(goodsManagementEntity.getSaleEndDtime())
                .items(toGoodsItemManagement(goodsManagementEntity.getItems()))
                .image(goodsManagementEntity.getImage())
                .addImage(goodsManagementEntity.getAddImage())
                .build();
    }

    public static List<GoodsItemManagement> toGoodsItemManagement(List<GoodsItemManagementEntity> goodsItemManagementEntityList) {
        return goodsItemManagementEntityList.stream()
                .map(GoodsItemManagement::new).collect(Collectors.toList());
    }

    public GoodsManagementEntity toEntity() {
        List<GoodsItemManagementEntity> goodsItemManagementEntityList = toGoodsItemManagementEntity(items);

        GoodsManagementEntity goodsManagementEntity =  GoodsManagementEntity.builder()
                .goodsNo(goodsNo)
                .goodsNm(goodsNm)
                .category(category)
                .salePrice(salePrice)
                .marketPrice(marketPrice)
                .supplyPrice(supplyPrice)
                .vendorId(vendorId)
                .stockQty(stockQty)
                .brandNm(brandNm)
                .saleStartDtime(saleStartDtime)
                .saleEndDtime(saleEndDtime)
                .image(image)
                .addImage(addImage)
                .build();

        goodsManagementEntity.addItems(goodsItemManagementEntityList);

        return goodsManagementEntity;
    }

    public List<GoodsItemManagementEntity> toGoodsItemManagementEntity(List<GoodsItemManagement> goodsItemManagementList) {
        return goodsItemManagementList.stream()
                .map(GoodsItemManagementEntity::new).collect(Collectors.toList());
    }

}
