package com.chenchao.controller.consumers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chenchao.service.IBookTypeService;
import com.chenchao.service.IWebParameterService;
import com.chenchao.model.BookDetails;
import com.chenchao.service.IBookDetailService;

import java.util.List;
import java.util.Random;

import com.chenchao.model.BookType;
import org.springframework.web.servlet.ModelAndView;
import com.chenchao.model.WebParameter;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private IBookTypeService bookTypeService;
    @Autowired
    private IWebParameterService webParameterService;
    @Autowired
    private IBookDetailService bookDetailService;

    private List<BookType> TypeList;
    @RequestMapping({"/use_index"})
    public ModelAndView index(HttpSession session){
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
        System.out.println("consumers_index");
        view.setViewName("consumers/index");

        return view;
    }
    @RequestMapping({"/admin_index"})
    public ModelAndView admin_index(HttpSession session){
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
        System.out.println("admin_index");
        view.setViewName("admin/admin_index");

        return view;
    }

    @RequestMapping({"", "/"})
    public ModelAndView visitor_index(){
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
        view.setViewName("visitor_index");

        return view;
    }
    /**
     * 点击首页导航栏分类后来到这个handler
     * Home page navigation
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("/index/Type/{typeId}")
    public ModelAndView bookListByCategoryId(@PathVariable("typeId") int typeId, Model model) {

        ModelAndView view = new ModelAndView();

        List<BookDetails> bookDetails = bookDetailService.findBookInfoHomePageNavigation(typeId, new Random().nextInt(3), 18);
        List<BookType> bookTypes = bookTypeService.getBookList();
        view.addObject("BT", bookTypes);
        view.addObject("bookDetails", bookDetails);
        view.addObject("typeId", typeId);
        view.setViewName("consumers/index");
        return view;
    }
}
