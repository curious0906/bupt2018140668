package com.chenchao.dao;

import com.chenchao.model.OrderParticulars;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Mapper

@Repository
public interface OrderDetailMapper {
    void insertOrder(OrderParticulars order);
    void deleteByExample(Example example);
    List<OrderParticulars> selectByExample(Example example);

    List<OrderParticulars> selectByOrderId(String orderId);
}
