package com.chenchao.controller.consumers;

import com.chenchao.pojo.BSResult;
import com.chenchao.model.BookDetails;
import com.chenchao.model.OrderForm;
import com.chenchao.service.IOrderDetailService;
import com.chenchao.service.IOrderService;
import com.chenchao.pay.Alipay;
import com.chenchao.pay.PayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PayController {

    @Autowired
    private Alipay alipay;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @RequestMapping("/{orderId}")
    public String paymentPage(@PathVariable("orderId") String orderId, HttpServletResponse response, Model model){


        BSResult bsResult = orderService.findOrderById(orderId);
        OrderForm order = (OrderForm)bsResult.getData();

        List<BookDetails> books = orderDetailService.findBooksByOrderId(order.getOrderFormId());

        PayContext payContext = new PayContext();
        payContext.setResponse(response);
        payContext.setOrders(order);
        payContext.setBookInfos(books);

        try {
            alipay.pay(payContext);
            System.out.println("13");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception", "支付出错了!");
            return "consumers/exception";
        }
        return "consumers/pay_success";
    }

    @RequestMapping("/return")
    public String returnUrl(String out_trade_no,String total_amount,String body,
                            Model model){

        model.addAttribute("body", body);
        model.addAttribute("total_amount", total_amount);


        BSResult bsResult = orderService.findOrderById(out_trade_no);
        OrderForm order = (OrderForm)bsResult.getData();

        List<BookDetails> books = orderDetailService.findBooksByOrderId(order.getOrderFormId());

        PayContext payContext = new PayContext();
        payContext.setOrders(order);
        payContext.setBookInfos(books);

        orderService.updateOrderAfterPay(payContext);


        return "consumers/pay_success";
    }

    @RequestMapping("/notify")
    public void notifyUrl(){

        System.out.println("notify--------------------------");



    }

}
