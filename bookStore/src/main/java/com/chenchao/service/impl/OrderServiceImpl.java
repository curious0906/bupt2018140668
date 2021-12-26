package com.chenchao.service.impl;

import com.chenchao.pojo.BSResult;
import com.chenchao.utils.BSResultUtil;
import com.chenchao.utils.IDUtils;
import com.chenchao.dao.BookInfoMapper;
import com.chenchao.dao.OrderDetailMapper;
import com.chenchao.dao.OrdersMapper;
import com.chenchao.dao.OrderShippingMapper;
//import com.chenchao.model.entity.*;
import com.chenchao.model.Users;
import com.chenchao.model.OrderForm;
import com.chenchao.model.OrderDelivery;
import com.chenchao.model.OrderParticulars;
import com.chenchao.model.BookDetails;
import com.chenchao.model.custom.Cart;
import com.chenchao.model.custom.CartItem;
import com.chenchao.dao.custom.CustomMapper;
import com.chenchao.model.custom.OrderCustom;
import com.chenchao.service.IOrderService;
import com.chenchao.pay.PayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.zdd.bookstore.model.entity.*;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private CustomMapper customMapper;

    @Autowired
    private OrdersMapper orderMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    //未完成状态，比如未付款等
    private final int NOT_COMPLETED = 0;

    private final int COMPLETED = 1;

    //未发货
    private final int NOT_POST = 2;
    //已发货
    private final int POSTED = 3;

    //确认收货
    private final int CONFIRM_REVEIVE = 4;

    @Override
    public List<OrderCustom> findOrdersByUserId(int userId) {
        return customMapper.findOrdersByUserId(userId);
    }

    @Override
    public List<OrderCustom> findOrdersByStoreId(int storeId) {
        return customMapper.findOrdersByStoreId(storeId);
    }

    /**
     * 创建订单
     *
     * @param cart
     * @param user
     * @return
     */
    @Override
    @Transactional
    public BSResult createOrder(Cart cart, Users user, String express, int payMethod) {
        Map<Integer, CartItem> cartItems = cart.getCartItems();

        if(cartItems.size() > 0){
            OrderForm order = new OrderForm();
            String orderId = IDUtils.genOrderId();
            order.setOrderFormId(orderId);
            order.setUserId(user.getUserId());
            order.setOrderCreateTime(new Date());
            order.setOrderUpdateTime(new Date());
            order.setBuyerWhetherEvaluated(NOT_COMPLETED);
            order.setShippingName(express);
            order.setOrderAmount(cartItems.size());
            order.setAmountOfRealPay(String.format("%.2f", cart.getTotal()));
            order.setPayType(payMethod);
            order.setOrderStatus(NOT_COMPLETED);
            order.setBuyerWhetherEvaluated(NOT_COMPLETED);
            order.setPostage("0.00");//邮费
            order.setTimeOfPayment(new Date());
            order.setTransactionCompleteTime(new Date());
            order.setTransactionCloseTime(new Date());
            order.setShippingCode(orderId);
            order.setBuyerMessage("");

            orderMapper.insertOrder(order);

            OrderDelivery orderShipping = new OrderDelivery();
            orderShipping.setOrderFormId(orderId);
            orderShipping.setOrderDeliveryCreated(new Date());
            orderShipping.setOrderDeliveryUpdated(new Date());
            orderShipping.setDeliveryAddress(user.getUserDetailAddress());
            orderShipping.setDeliveryMobile(user.getUserPhone());
            orderShipping.setDeliveryName(user.getUserUsername());
            orderShipping.setDeliveryZipCode(user.getUserPostalCode());
            orderShipping.setDeliveryState("广东");
            orderShipping.setDeliveryCity("广州");
            orderShipping.setDeliveryDistrictCounty("海珠区");
            orderShipping.setDeliveryPhone("15970933534");
            orderShippingMapper.insertOrder(orderShipping);

            List<OrderParticulars> orderDetails = new ArrayList<>();
            for (Map.Entry<Integer, CartItem> cartItemEntry : cartItems.entrySet()) {

                CartItem cartItem = cartItemEntry.getValue();
                if (cartItem.getBuyNum() > 0 && cartItem.isChecked()) {
                    OrderParticulars orderDetail = new OrderParticulars();
                    orderDetail.setOrderFormId(orderId);
                    orderDetail.setBookId(cartItemEntry.getKey());
                    orderDetail.setSellQuantity(cartItem.getBuyNum());
                    orderDetail.setOrderCode(orderId);
                    orderDetail.setPaymentAndDeliveryStatus(NOT_COMPLETED + "");
                    orderDetail.setReceivingStatus(NOT_COMPLETED + "");
                    orderDetail.setShopId((int)cartItem.getBookInfo().getBookShopId());
                    orderDetail.setTotalPrice(BigDecimal.valueOf(cartItem.getSubTotal()));
                    orderDetail.setPricePerUnit(cartItem.getBookInfo().getBookPrice());
                    orderDetail.setImageUrl(cartItem.getBookInfo().getBookImageUrl());
                    orderDetail.setBookName(cartItem.getBookInfo().getBookName());
                    orderDetail.setShoppingTime(new Date());
                    orderDetails.add(orderDetail);
                    orderDetailMapper.insertOrder(orderDetail);
                }
            }
            return BSResultUtil.success(order);
        }else
            return BSResultUtil.build(400, "没有选中的购物项，创建订单失败!");
    }

    /**
     * 通过订单号查询订单
     * @param orderId
     * @return
     */
    @Override
    public BSResult findOrderById(String orderId) {
        OrderForm orders = orderMapper.selectByPrimaryKey(orderId);
        return BSResultUtil.success(orders);
    }

    @Override
    @Transactional
    public BSResult deleteOrder(String orderId) {

        Example example = new Example(OrderParticulars.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        orderDetailMapper.deleteByExample(example);

        orderMapper.deleteByPrimaryKey(orderId);
        orderShippingMapper.deleteByPrimaryKey(orderId);

        return BSResultUtil.success();
    }

    /**
     * 支付后
     * @param payContext
     */
    @Override
    public void updateOrderAfterPay(PayContext payContext) {
        //更新订单
        OrderForm order = payContext.getOrders();
        order.setOrderUpdateTime(new Date());
        order.setTimeOfPayment(new Date());
        order.setOrderStatus(COMPLETED);
        order.setTimeOfPayment(new Date());
        orderMapper.updateByPrimaryKey(order);

        //更新库存
        List<BookDetails> books = payContext.getBookInfos();

        Example example = new Example(OrderParticulars.class);
        Example.Criteria criteria = example.createCriteria();


        books.forEach(bookInfo -> {
            example.clear();
            criteria.andEqualTo("bookId", bookInfo.getBookId());
            List<OrderParticulars> orderDetails = orderDetailMapper.selectByExample(example);
            if(orderDetails != null && !orderDetails.isEmpty()){
                bookInfo.setBookShopMount(bookInfo.getBookShopMount()-orderDetails.get(0).getSellQuantity());
                bookInfo.setBookDealMount(bookInfo.getBookDealMount()+orderDetails.get(0).getSellQuantity());
            }
            bookInfoMapper.updateByPrimaryKey(bookInfo);
        });

    }

    @Override
    public BSResult postOrder(String orderId) {
        OrderForm orders = new OrderForm();
        orders.setOrderFormId(orderId);
        try {
            orders.setOrderStatus(POSTED);
            orderMapper.updateByPrimaryKeySelective(orders);
        } catch (Exception e) {
            orders.setOrderStatus(NOT_POST);
            orderMapper.updateByPrimaryKeySelective(orders);
            e.printStackTrace();
            return BSResultUtil.build(500, "发货失败");
        }
        return BSResultUtil.success();
    }

    @Override
    public OrderCustom findOrderCustomById(String orderId) {
        OrderCustom orderCustom = new OrderCustom();
        OrderForm orders = orderMapper.selectByPrimaryKey(orderId);
        Example example = new Example(OrderParticulars.class);
        example.createCriteria().andEqualTo("orderId", orderId);
        List<OrderParticulars> orderDetails = orderDetailMapper.selectByExample(example);
        OrderDelivery orderShipping = orderShippingMapper.selectByPrimaryKey(orderId);
        orderCustom.setOrderForm(orders);
        orderCustom.setOrderParticularsList(orderDetails);
        orderCustom.setOrderDelivery(orderShipping);
        return orderCustom;
    }

    @Override
    public BSResult updateOrder(OrderCustom orderCustom) {
        orderMapper.updateByPrimaryKeySelective(orderCustom.getOrderForm());
        orderShippingMapper.updateByPrimaryKeySelective(orderCustom.getOrderDelivery());
        return BSResultUtil.success();
    }

    @Override
    public BSResult confirmReceiving(String orderId) {
        OrderForm order = new OrderForm();
        order.setOrderFormId(orderId);
        order.setOrderStatus(CONFIRM_REVEIVE);
        int i = orderMapper.updateByPrimaryKeySelective(order);
        if(i > 0){
            return BSResultUtil.success();
        }
        return BSResultUtil.build(400, "确认收货失败!");
    }
}
