package com.chenchao.pay;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.chenchao.model.BookDetails;
import com.chenchao.model.OrderForm;
public class PayContext {

    private OrderForm orderForm;

    private List<BookDetails> bookDetails;

    private HttpServletResponse response;

    public List<BookDetails> getBookInfos() {
        return bookDetails;
    }

    public void setBookInfos(List<BookDetails> bookInfos) {
        this.bookDetails = bookInfos;
    }

    public OrderForm getOrders() {
        return orderForm;
    }

    public void setOrders(OrderForm orderForm) {
        this.orderForm = orderForm;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}