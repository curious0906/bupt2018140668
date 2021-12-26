package com.chenchao.controller.consumers;

import com.github.pagehelper.PageInfo;
import com.chenchao.model.BookDetails;
import com.chenchao.model.BookType;
import com.chenchao.service.IBookDetailService;
import com.chenchao.service.IBookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
public class BookDetailsController {

    @Autowired
    private IBookDetailService bookDetailService;
    private IBookTypeService bookTypeService;

    public BookDetailsController(IBookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;
    }

    /**
     * 通过关键字和书籍分类搜索书籍列表
     *
     * @param keywords
     * @return
     */
    @RequestMapping("/list")
    public String bookSearchList(@RequestParam(defaultValue = "", required = false) String keywords,
                                 @RequestParam(defaultValue = "0", required = false) int typeId,//分类Id，默认为0，即不按照分类Id查
                                 @RequestParam(defaultValue = "1", required = false) int page,
                                 @RequestParam(defaultValue = "6", required = false) int pageSize,
                                 Model model) {
        keywords = keywords.trim();
        PageInfo<BookDetails> bookPageInfo = bookDetailService.findBookListByKeywordsOrType(keywords, typeId, page, pageSize,0);//storeId为0，不按照商店Id查询

        model.addAttribute("bookPageInfo", bookPageInfo);

        model.addAttribute("keywords", keywords);

        model.addAttribute("typeId", typeId);
        return "consumers/bookList";
    }
    /**
     * 通过关键字和书籍分类搜索书籍列表
     *
     * @param bookId
     * @return
     */
    @RequestMapping("/info")
    public String bookInfo(@RequestParam(defaultValue = "", required = false) int bookId,
                                 Model model) {
        BookDetails bookInfo = bookDetailService.queryBookAvailable(bookId);//storeId为0，不按照商店Id查询
        int bookTypeId = (int)bookInfo.getBookTypeId();
        BookType bookType = bookTypeService.getBookType(bookTypeId);

        model.addAttribute("bookInfo", bookInfo);
        model.addAttribute("bookType", bookType);
        return "consumers/book_info";
    }
}
