package com.chenchao.controller.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.chenchao.exception.BSException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import com.chenchao.pojo.BSResult;
import com.chenchao.model.custom.OrderCustom;
import com.chenchao.service.IOrderService;
import com.chenchao.service.ICartService;
import com.chenchao.service.IBookDetailService;


import com.chenchao.model.Users;
import com.chenchao.model.custom.Cart;
import com.chenchao.model.BookDetails;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private IBookDetailService bookInfoService;

    /**
     * 填写订单信息页面
     *
     * @param bookId
     * @param buyNum
     * @param request
     * @return
     */
    @GetMapping("/info")
    public String orderInfo(@RequestParam(required = false, defaultValue = "0") int bookId,
                            @RequestParam(required = false, defaultValue = "0") int buyNum,
                            HttpServletRequest request) throws BSException {

        if (bookId != 0) {
            //点了立即购买，放到request域中，也session的立即购买域中以区分购物车中的书籍
            BookDetails bookInfo = bookInfoService.findById(bookId);
            if (bookInfo != null) {
                BSResult bsResult = cartService.addToCart(bookInfo, null, buyNum);
                request.getSession().setAttribute("buyNowCart", bsResult.getData());
                request.setAttribute("cart", bsResult.getData());
                return "consumers/order_info";
            } else {
                request.setAttribute("exception", "不好意思，书籍库存不足或不存在了！");
                return "consumers/exception";
            }
        }
        //没有点立即购买，购物车中的总金额大于0才让填写订单信息
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null && cart.getTotal() > 0) {
            return "consumers/order_info";
        } else {
            return "consumers/cart";
        }


    }

    @GetMapping("/payPage/{orderId}")
    public String toPay(@PathVariable("orderId") String orderId, Model model) {

        BSResult bsResult = orderService.findOrderById(orderId);

        if (bsResult.getCode() == 200) {
            model.addAttribute("order", bsResult.getData());
            return "consumers/payment";
        }
        return "consumers/exception";
    }

    @RequestMapping("/deletion/{orderId}")
    public String deletion(@PathVariable("orderId") String orderId) {

        BSResult bsResult = orderService.deleteOrder(orderId);

        if (bsResult.getCode() == 200) {
            return "redirect:/consumers/order/list";
        }
        return "consumers/exception";
    }

    /**
     * 订单列表
     *
     * @return
     */
    @GetMapping("/list")
    public String orderList(HttpServletRequest request) {

        Users loginUser = (Users) request.getSession().getAttribute("loginUser");

        List<OrderCustom> orderCustoms = orderService.findOrdersByUserId(loginUser.getUserId());

        request.setAttribute("orderCustoms", orderCustoms);

        return "consumers/order_list";
    }

    /**
     * 创建订单
     *
     * @return
     */
    @PostMapping("/creation")
    public String createOrder(Users userDTO, String express, int payMethod, HttpServletRequest request) {

        //立即购买,优先创建订单
        Cart buyNowCart = (Cart) request.getSession().getAttribute("buyNowCart");

        Users loginUser = (Users) request.getSession().getAttribute("loginUser");
        userDTO.setUserId(loginUser.getUserId());
        userDTO.setUserPostalCode(loginUser.getUserPostalCode());

        if (buyNowCart != null) {
            BSResult bsResult = orderService.createOrder(buyNowCart, userDTO, express, payMethod);

            if (bsResult.getCode() == 200) {
                request.setAttribute("order", bsResult.getData());
                cartService.clearCart(request, "buyNowCart");
                return "consumers/payment";
            } else {
                request.setAttribute("exception", bsResult.getMessage());
                return "consumers/exception";
            }
        }

        //普通购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            BSResult bsResult = orderService.createOrder(cart, userDTO, express, payMethod);

            if (bsResult.getCode() == 200) {
                request.setAttribute("order", bsResult.getData());
                cartService.clearCart(request, "cart");
                return "consumers/payment";
            } else {
                request.setAttribute("exception", bsResult.getMessage());
                return "consumers/exception";
            }

        } else {
            request.setAttribute("exception", "购物车为空！");
            return "consumers/exception";
        }

    }

    /**
     * 确认收货
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/confirm/{orderId}")
    public String confirmReceiving(@PathVariable("orderId") String orderId, Model model) {
        BSResult bsResult = orderService.confirmReceiving(orderId);

        if (bsResult.getCode() == 200) {
            return "redirect:/order/list";
        } else {
            model.addAttribute("exception", bsResult.getMessage());
            return "consumers/exception";
        }

    }
}
