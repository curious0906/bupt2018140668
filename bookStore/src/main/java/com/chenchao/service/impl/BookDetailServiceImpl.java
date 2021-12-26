package com.chenchao.service.impl;
import com.chenchao.model.BookDetails;
import com.chenchao.pojo.BSResult;
import com.chenchao.service.IBookDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import com.chenchao.dao.BookDetailsDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class BookDetailServiceImpl implements IBookDetailService{
    @Autowired
    private BookDetailsDao bookDetail;

    @Override
    @Cacheable(value="book",key = "'bookInfo_'+#TypeId+'_'+#currentPage+#pageSize")
    public List<BookDetails> findIndexBookList(int TypeId, int currentPage, int pageSize) {
        //接口入口参数设置分页信息，当前页，每页显示多少
        //接口实现逻辑每页展示18个本图书，包括新书上架8本和十本好书推荐，图书根据类别进行展示，然后根据图书成交量递减和游览量递减进行排序
        //接口返回值类型List<BookDetails>

        //先以typeId 为条件选择图书,然后book_look_mount、book_deal_mount字段递减,
        //最后选currentPage到currentPage*18个图书返回
        //select * from (select * from book_details where book_type_id=2 order by book_deal_mount DESC,book_look_mount DESC) as a limit (currentPage-1)*18,currentPage*18;
        System.out.println("TypeId"+TypeId);
        //起始位置，结束位置
        int startingPosition = (currentPage)*pageSize;
        int endingPosition = (currentPage+1)*pageSize;
        System.out.println("endingPosition"+endingPosition);
        List<BookDetails> BookDetailsList = bookDetail.findBookListByTypeId(TypeId,startingPosition,endingPosition);
        System.out.println("BookDetailsList"+BookDetailsList.size());
        return BookDetailsList;
    }

    /**
     * 按照若干条件搜索书籍，查询关键字可以是书名、关键字或ISBN
     *
     * @param keywords
     * @param typeId
     * @param page
     * @param pageSize
     * @param shopId
     * @return
     */
    @Override
    public PageInfo<BookDetails> findBookListByKeywordsOrType(String keywords, int typeId, int page, int pageSize, int shopId) {


        PageHelper.startPage(page, pageSize);
        List<BookDetails> books = bookDetail.findBookInfo(page, pageSize);
        if (!StringUtils.isEmpty(keywords)) {
            PageHelper.startPage(page, pageSize);
            String s = "%" + keywords + "%";
            books = bookDetail.findBookInfoByKeywords(s, page, pageSize);
        }
        else if (typeId != 0) {
            //加分类Id查询条件,where (name like ? or author like ? or isbn like ?) and typeId = ?
            PageHelper.startPage(page, pageSize);
            books = bookDetail.findBookInfoByTypeId(typeId, page, pageSize);
        }

        else if (shopId == 0) {
            //前台展示，是否为上架书籍
            PageHelper.startPage(page, pageSize);
            books = bookDetail.findBookInfoByIsShelf(page, pageSize);
        }
        else{
            //后台管理
            PageHelper.startPage(page, pageSize);
            books = bookDetail.findBookInfoByStoreId(shopId);
        }
        PageInfo<BookDetails> pageInfo = new PageInfo<>(books);

        return pageInfo;
    }

    @Override
    public List<BookDetails> findBookInfoHomePageNavigation(int typeId, int page, int pageSize) {
        return bookDetail.findBookInfoInHomePageNavigation(typeId,page,pageSize);
    }

    @Override
    public BSResult saveBook(BookDetails bookInfo, String bookDescStr) {
        return null;
    }

    @Override
    public BookDetails queryBookAvailable(int bookId) {
        return bookDetail.queryBookAvailable(bookId);
    }

    @Override
    public BookDetails adminFindById(int bookId) {
        return null;
    }

    @Override
    public BookDetails findById(long bookId) {
        return null;
    }

    @Override
    public void updateBook(BookDetails bookInfo, String bookDesc) {

    }

    @Override
    public void deleteBook(int bookId) {

    }

    @Override
    public void changeShelfStatus(int bookId, int isShelf) {

    }
}
