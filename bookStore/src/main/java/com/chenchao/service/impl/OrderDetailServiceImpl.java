package com.chenchao.service.impl;


import com.chenchao.dao.BookInfoMapper;
import com.chenchao.dao.OrderDetailMapper;
import com.chenchao.model.BookDetails;
import com.chenchao.model.OrderParticulars;
import com.chenchao.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public List<BookDetails> findBooksByOrderId(String orderId) {
        System.out.println("12");
//        Example example = new Example(OrderParticulars.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("orderId", orderId);
        List<OrderParticulars> orderDetails = orderDetailMapper.selectByOrderId(orderId);

        List<BookDetails> bookInfos = orderDetails.stream()
                .map(orderDetail -> bookInfoMapper.selectByPrimaryKey(orderDetail.getBookId()))
                .collect(Collectors.toList());

        return bookInfos;
    }

}

