package com.chenchao.service;


import com.chenchao.model.BookDetails;

import java.util.List;

public interface IOrderDetailService {

    List<BookDetails> findBooksByOrderId(String orderId);
}

