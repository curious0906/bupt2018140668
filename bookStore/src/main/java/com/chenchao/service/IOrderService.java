package com.chenchao.service;

import com.chenchao.model.Users;
import com.chenchao.model.custom.OrderCustom;
import com.chenchao.pojo.BSResult;
import java.util.List;
import com.chenchao.pay.PayContext;
import com.chenchao.model.custom.Cart;

public interface IOrderService {

    List<OrderCustom> findOrdersByUserId(int userId);

    List<OrderCustom> findOrdersByStoreId(int storeId);

    BSResult createOrder(Cart cart, Users user, String express, int payMethod);

    BSResult findOrderById(String orderId);

    BSResult deleteOrder(String orderId);

    void updateOrderAfterPay(PayContext payContext);

    BSResult postOrder(String orderId);

    OrderCustom findOrderCustomById(String orderId);

    BSResult updateOrder(OrderCustom orderCustom);

    BSResult confirmReceiving(String orderId);
}
