package com.chenchao.dao;
import com.chenchao.model.OrderDelivery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper

@Repository
public interface OrderShippingMapper {
    void insertOrder(OrderDelivery order);
    void deleteByPrimaryKey(String orderId);
    OrderDelivery selectByPrimaryKey(String orderId);
    void updateByPrimaryKeySelective(OrderDelivery orderDelivery);
}
