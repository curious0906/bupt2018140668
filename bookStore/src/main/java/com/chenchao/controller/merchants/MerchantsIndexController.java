package com.chenchao.controller.merchants;

import com.chenchao.model.BookDetails;
import com.chenchao.model.BookType;
import com.chenchao.model.WebParameter;
import com.chenchao.service.IBookDetailService;
import com.chenchao.service.IBookTypeService;
import com.chenchao.service.IWebParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping
public class MerchantsIndexController {
    @Autowired
    private IBookTypeService bookTypeService;
    @Autowired
    private IWebParameterService webParameterService;
    @Autowired
    private IBookDetailService bookDetailService;

    private List<BookType> TypeList;

    @RequestMapping({"/merchants_index"})
    public ModelAndView MerchantsIndex() {
        if(TypeList == null){
            TypeList = bookTypeService.getBookList();
        }
        //获得书籍列表
        ModelAndView view = new ModelAndView();
        List<BookType> bookTypes = bookTypeService.getBookList();
        WebParameter webParameter = webParameterService.selectWebParameter();
        //获得书籍详情列表
        List<BookDetails> bookDetails = bookDetailService.findIndexBookList(TypeList.get(new Random().nextInt(6)).getBookTypeId(), new Random().nextInt(2), 18);
        view.addObject("bookDetails", bookDetails);
//        model.addAttribute("bookTypes",bookTypes);
        view.addObject("BT", bookTypes);
        view.addObject("webParameter", webParameter);
//        session.getAttribute("user");
        view.setViewName("merchants/business_index");
        return view;
    }

    @RequestMapping({"/merchants"})
    public String MerchantsIndexTheBackground() {

        return "merchants/merchants_index";
    }
}
