package com.chenchao.service;

import com.chenchao.pojo.BSResult;
import com.chenchao.model.custom.Cart;
import com.chenchao.model.BookDetails;

import javax.servlet.http.HttpServletRequest;

public interface ICartService {

    BSResult addToCart(BookDetails bookInfo, Cart cart,int buyNum);

    BSResult clearCart(HttpServletRequest request,String sessionName);

    BSResult deleteCartItem(int bookId, HttpServletRequest request);

    BSResult updateBuyNum(int bookId, int newNum, HttpServletRequest request);

    BSResult checkedOrNot(Cart cart,int bookId);

}