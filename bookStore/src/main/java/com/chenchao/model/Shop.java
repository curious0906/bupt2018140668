package com.chenchao.model;

import java.util.Date;

public class Shop {
    private Integer shopId;
    private Integer shopManagerId;
    private String shopPhoneNumber;
    private String shopTelephone;
    private String shopName;
    private String shopPosition;
    private Date shopCreated;
    private Date shopUpdated;

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setShopManagerId(Integer shopManagerId) {
        this.shopManagerId = shopManagerId;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopPosition(String shopPosition) {
        this.shopPosition = shopPosition;
    }

    public void setShopCreated(Date shopCreated) {
        this.shopCreated = shopCreated;
    }

    public void setShopUpdated(Date shopUpdated) {
        this.shopUpdated = shopUpdated;
    }


    public Integer getShopId() {
        return shopId;
    }

    public Integer getShopManagerId() {
        return shopManagerId;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopPosition() {
        return shopPosition;
    }

    public Date getShopCreated() {
        return shopCreated;
    }

    public Date getShopUpdated() {
        return shopUpdated;
    }



}
