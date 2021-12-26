package com.chenchao.model.custom;

import com.chenchao.model.OrderForm;
import com.chenchao.model.OrderDelivery;
import com.chenchao.model.OrderParticulars;
import java.util.List;

/**
 * 自定义Order实体，包括订单物流信息，订单详情，非mybaits逆向工程生成
 */
public class OrderCustom {


    private String orderFormId;
    private OrderForm orderForm;
    private OrderDelivery orderDelivery;

    /**
     * 一个订单包括多个订单信息
     */
    private List<OrderParticulars> orderParticularsList;

    public void setOrderId(String orderFormId) {
        this.orderFormId = orderFormId;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
    }

    public void setOrderDelivery(OrderDelivery orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public void setOrderParticularsList(List<OrderParticulars> orderParticularsList) {
        this.orderParticularsList = orderParticularsList;
    }


    public String getOrderId() {
        return orderFormId;
    }

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public OrderDelivery getOrderDelivery() {
        return orderDelivery;
    }

    public List<OrderParticulars> getOrderParticularsList() {
        return orderParticularsList;
    }

}
