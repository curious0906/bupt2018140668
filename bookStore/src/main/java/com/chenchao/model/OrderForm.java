package com.chenchao.model;

import java.util.Date;

public class OrderForm {
    private String orderFormId;
    private Integer userId;
    private String amountOfRealPay;
    private Integer payType;
    private Integer orderStatus;
    private String postage;
    private Date orderCreateTime;
    private Date orderUpdateTime;
    private Integer orderAmount;
    private Date timeOfPayment;
    private Date transactionCompleteTime;
    private Date transactionCloseTime;
    private String shippingName;
    private String shippingCode;
    private String buyerMessage;
    private Integer buyerWhetherEvaluated;

    public void setOrderFormId(String orderFormId) {
        this.orderFormId = orderFormId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAmountOfRealPay(String amountOfRealPay) {
        this.amountOfRealPay = amountOfRealPay;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public void setOrderUpdateTime(Date orderUpdateTime) {
        this.orderUpdateTime = orderUpdateTime;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setTimeOfPayment(Date timeOfPayment) {
        this.timeOfPayment = timeOfPayment;
    }

    public void setTransactionCompleteTime(Date transactionCompleteTime) {
        this.transactionCompleteTime = transactionCompleteTime;
    }

    public void setTransactionCloseTime(Date transactionCloseTime) {
        this.transactionCloseTime = transactionCloseTime;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public void setBuyerWhetherEvaluated(Integer buyerWhetherEvaluated) {
        this.buyerWhetherEvaluated = buyerWhetherEvaluated;
    }



    public String getOrderFormId() {
        return orderFormId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getAmountOfRealPay() {
        return amountOfRealPay;
    }

    public Integer getPayType() {
        return payType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public String getPostage() {
        return postage;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public Date getOrderUpdateTime() {
        return orderUpdateTime;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public Date getTimeOfPayment() {
        return timeOfPayment;
    }

    public Date getTransactionCompleteTime() {
        return transactionCompleteTime;
    }

    public Date getTransactionCloseTime() {
        return transactionCloseTime;
    }

    public String getShippingName() {
        return shippingName;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public Integer getBuyerWhetherEvaluated() {
        return buyerWhetherEvaluated;
    }

    public String getStatusString(){
        if(orderStatus == 0){
            return "未付款";
        }else if(orderStatus == 1){
            return "已付款";
        }else if(orderStatus == 2){
            return "未发货";
        }else if(orderStatus == 3){
            return "已发货";
        }else if(orderStatus == 4){
            return "确认收货";
        }else{
            return "交易关闭";
        }
    }

}
