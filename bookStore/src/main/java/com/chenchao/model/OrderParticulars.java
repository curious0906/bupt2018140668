package com.chenchao.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderParticulars {
    private Integer orderParticularsId;
    private String orderCode;
    private String orderFormId;
    private Integer bookId;
    private Integer shopId;
    private Integer sellQuantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
    private String paymentAndDeliveryStatus;
    private Date shoppingTime;
    private String receivingStatus;
    private String imageUrl;
    private String bookName;
    public void setOrderParticularsId(Integer orderParticularsId) {
        this.orderParticularsId = orderParticularsId;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setOrderFormId(String orderFormId) {
        this.orderFormId = orderFormId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public void setSellQuantity(Integer sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaymentAndDeliveryStatus(String paymentAndDeliveryStatus) {
        this.paymentAndDeliveryStatus = paymentAndDeliveryStatus;
    }

    public void setShoppingTime(Date shoppingTime) {
        this.shoppingTime = shoppingTime;
    }

    public void setReceivingStatus(String receivingStatus) {
        this.receivingStatus = receivingStatus;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public Integer getOrderParticularsId() {
        return orderParticularsId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getOrderFormId() {
        return orderFormId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public Integer getSellQuantity() {
        return sellQuantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentAndDeliveryStatus() {
        return paymentAndDeliveryStatus;
    }

    public Date getShoppingTime() {
        return shoppingTime;
    }

    public String getReceivingStatus() {
        return receivingStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBookName() {
        return bookName;
    }


}
