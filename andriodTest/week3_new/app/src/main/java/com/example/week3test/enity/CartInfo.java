package com.example.week3test.enity;

public class CartInfo {
    public int id;
    public int goodsId;
    public int count;
    // 商品信息
    public GoodsInfo goods;

    public CartInfo(int id, int goodsId, int count) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
        this.goods = new GoodsInfo();
    }

    public CartInfo() {
    }
}
