package com.chenchao.pay;

import com.chenchao.pojo.BSResult;
import com.chenchao.utils.BSResultUtil;

public abstract class AbstractPay implements IPayService {

    @Override
    public BSResult pay(PayContext payContext) throws Exception {
        prepareAndPay(payContext);
        afterPay(payContext);
        return BSResultUtil.success();
    }


}
