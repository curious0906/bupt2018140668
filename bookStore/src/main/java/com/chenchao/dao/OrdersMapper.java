package com.chenchao.dao;
import com.chenchao.model.OrderForm;
import com.chenchao.model.OrderParticulars;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper

@Repository
public interface OrdersMapper {
    void insertOrder(OrderForm order);

    OrderForm selectByPrimaryKey(String orderId);
    void deleteByPrimaryKey(String orderId);
    void updateByPrimaryKey(OrderForm order);
    int updateByPrimaryKeySelective(OrderForm orders);
}
