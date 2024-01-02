package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model;


public class GiftModel {
    int gift;
    String name;
    String price;

    public GiftModel() {
    }

    public GiftModel(String str, String str2, int i) {
        this.name = str;
        this.price = str2;
        this.gift = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public int getGift() {
        return this.gift;
    }

    public void setGift(int i) {
        this.gift = i;
    }
}
