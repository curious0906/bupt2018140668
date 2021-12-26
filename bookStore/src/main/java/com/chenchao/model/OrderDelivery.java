package com.chenchao.model;

import java.util.Date;

public class OrderDelivery {
    private String orderFormId;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryMobile;
    private String deliveryState;
    private String deliveryCity;
    private String deliveryDistrictCounty;
    private String deliveryAddress;
    private String deliveryZipCode;
    private Date orderDeliveryCreated;
    private Date orderDeliveryUpdated;

    public void setOrderFormId(String orderFormId) {
        this.orderFormId = orderFormId;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public void setDeliveryMobile(String deliveryMobile) {
        this.deliveryMobile = deliveryMobile;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public void setDeliveryDistrictCounty(String deliveryDistrictCounty) {
        this.deliveryDistrictCounty = deliveryDistrictCounty;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryZipCode(String deliveryZipCode) {
        this.deliveryZipCode = deliveryZipCode;
    }

    public void setOrderDeliveryCreated(Date orderDeliveryCreated) {
        this.orderDeliveryCreated = orderDeliveryCreated;
    }

    public void setOrderDeliveryUpdated(Date orderDeliveryUpdated) {
        this.orderDeliveryUpdated = orderDeliveryUpdated;
    }

    public String getOrderFormId() {
        return orderFormId;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public String getDeliveryMobile() {
        return deliveryMobile;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public String getDeliveryDistrictCounty() {
        return deliveryDistrictCounty;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryZipCode() {
        return deliveryZipCode;
    }

    public Date getOrderDeliveryCreated() {
        return orderDeliveryCreated;
    }

    public Date getOrderDeliveryUpdated() {
        return orderDeliveryUpdated;
    }


}
