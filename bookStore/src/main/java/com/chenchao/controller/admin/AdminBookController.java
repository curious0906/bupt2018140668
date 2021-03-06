package com.chenchao.controller.admin;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.chenchao.service.IBookDetailService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

import com.chenchao.dao.BookDescDao;
import com.chenchao.model.BookChara;
import com.chenchao.model.BookDetails;
import com.chenchao.model.Shop;
import com.chenchao.exception.BSException;
import com.chenchao.utils.IDUtils;

@Controller
@RequestMapping("/admin/book")
//@RequiresPermissions("book-manage")
public class AdminBookController {

    @Autowired
    private IBookDetailService bookDetailService;

    @Autowired
    private BookDescDao bookDescDao;


    @Value("${image.url.prefix}")
    private String urlPrefix;

    @RequestMapping("toAddition")
    @RequiresPermissions("book-add")
    public String toAddition() {
        return "admin/book/add";
    }

    @RequestMapping("/addition")
    @RequiresPermissions("book-add")
    public String addBook(BookDetails bookInfo, String bookDesc, MultipartFile pictureFile, HttpServletRequest request) throws Exception {

        uploadPicture(bookInfo, pictureFile, request);
        bookDetailService.saveBook(bookInfo, bookDesc);

        return "redirect:/admin/book/list";
    }

    @RequestMapping(value = "/list")
//    @RequiresPermissions("book-query")
    public String bookList(@RequestParam(defaultValue = "", required = false) String keywords,
                           @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                           HttpSession session,
                           Model model) {
        keywords = keywords.trim();
        Shop store = (Shop) session.getAttribute("loginStore");

//
//        if (store != null) {
//            PageInfo<BookDetails> books = bookDetailService.findBookListByCondition(keywords, 0, page, 10, store.getStoreId());
//            model.addAttribute("bookPageInfo", books);
//            model.addAttribute("keywords", keywords);
//        } else {
//            model.addAttribute("exception", "???????????????????????????");
//            return "exception";
//        }

//        return "admin/book/list";
        return "admin/book/list";
    }

    /**
     * ??????????????????
     *
     * @param bookId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/echo")
    @RequiresPermissions("book-edit")
    public String echo(int bookId, Model model) throws BSException {

        BookDetails bookInfo = bookDetailService.adminFindById(bookId);

        BookChara bookDesc = bookDescDao.selectByPrimaryKey(bookInfo.getBookId());

        model.addAttribute("bookInfo", bookInfo);

        model.addAttribute("bookDesc", bookDesc);

        return "admin/book/edit";
    }

    @RequestMapping("/update")
    @RequiresPermissions("book-edit")
    public String updateBook(BookDetails bookInfo, String bookDesc, String keywords, MultipartFile pictureFile, HttpServletRequest request, RedirectAttributes ra) throws Exception {
        uploadPicture(bookInfo, pictureFile, request);
        BookDetails originBook = bookDetailService.findById(bookInfo.getBookId());
        bookDetailService.updateBook(bookInfo, bookDesc);

        //???????????????????????????????????????
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + originBook.getBookImageUrl());
        uploadPic.delete();
        //????????????????????????
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    @RequestMapping("/deletion/{bookId}")
    @RequiresPermissions("book-delete")
    public String deletion(@PathVariable("bookId") int bookId, String keywords, RedirectAttributes ra, HttpServletRequest request) throws BSException {
        BookDetails bookInfo = bookDetailService.findById(bookId);
        String realPath = request.getServletContext().getRealPath("/");
        File uploadPic = new File(realPath + bookInfo.getBookImageUrl());
        uploadPic.delete();
        bookDetailService.deleteBook(bookId);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    @RequestMapping("/shelf")
    @RequiresPermissions("book-shelf")
    public String bookOffShelf(int bookId, int isShelf, String keywords, RedirectAttributes ra) {

        bookDetailService.changeShelfStatus(bookId, isShelf);
        ra.addAttribute("keywords", keywords);
        return "redirect:/admin/book/list";
    }

    private void uploadPicture(BookDetails bookInfo, MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        if (pictureFile != null) {
            if (!StringUtils.isEmpty(pictureFile.getOriginalFilename())) {
                String realPath = request.getServletContext().getRealPath("/" + urlPrefix);
                //??????????????????
                String pictureFileName = pictureFile.getOriginalFilename();
                //???????????????
                String newFileName = IDUtils.genShortUUID() + pictureFileName.substring(pictureFileName.lastIndexOf("."));

                //????????????
                File uploadPic = new File(realPath + File.separator + newFileName);

                //??????????????????
                pictureFile.transferTo(uploadPic);
                bookInfo.setBookImageUrl(urlPrefix + File.separator + newFileName);
            }
        }
    }

}
