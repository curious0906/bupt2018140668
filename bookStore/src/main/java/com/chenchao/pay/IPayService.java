package com.chenchao.pay;


import com.chenchao.pojo.BSResult;

public interface IPayService {

    BSResult pay(PayContext payContext) throws Exception;
    void prepareAndPay(PayContext payContext) throws Exception;
    void afterPay(PayContext payContext) throws Exception;

}
