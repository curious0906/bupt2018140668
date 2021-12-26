package com.chenchao.spider;

import com.chenchao.model.StackRoom;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * Jsoup可以解析HTML得到Document文档对象，
 * * 通常我们需要爬取的内容是文档中的某些东西而不是整个HTML内容，
 * * 这时就需要使用select选择器来提取我们需要的内容，
 * * select选择器的使用类似于jquery的使用方式
 */
public class BookParse {
    public static List<StackRoom> getData(HttpClient httpclient, String html, String category) throws ParseException, IOException {
        List<StackRoom> datas = new ArrayList<StackRoom>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("div[id=search_nature_rg]").select("ul[class=bigimg]").select("li");
        //1.创建连接client
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //2.设置连接的相关选项
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);  //需要解析js
        webClient.getOptions().setThrowExceptionOnScriptError(false);  //解析js出错时不抛异常
        int i = 1;
        for (Element element : elements) {

            String imgUrl = element.select("a[class=pic]").select("img").attr("data-original");
            if(StringUtils.isEmpty(imgUrl)){
                imgUrl = element.select("a[class=pic]").select("img").attr("src");
            }
            String bookName = element.select("a[class=pic]").select("img").attr("alt");
            String outline = element.select("p[class=name]").select("a").text();
            String detail = element.select("p[class=detail]").text();

            Elements authorList = element.select("p[class=search_book_author]").select("span");

            String author = authorList.get(0).select("a").attr("title");
            String publishDate = authorList.get(1).text().substring(authorList.get(1).text().indexOf("/") + 1).trim();
            String press = authorList.get(2).select("a").text();


            String bookRobPrice = element.select("p[class=price]").select("span[class=search_now_price]").text().substring(1);

            String bookMarketPrice = element.select("p[class=price]").select("span[class=search_pre_price]").text().substring(1);
            String discount = element.select("p[class=price]").select("span[class=search_discount]").text();

            String bookSize = "";
            String bookPackage = "";
            String ISBN = "";
            String catalog = "";
            /**
             * 爬取商品详情页的信息
             */

            String bookInfoUrl = element.select("a[class=pic]").attr("href");
            webClient.getOptions().setTimeout(10000);  //超时时间  ms
            //3.抓取页面
            HtmlPage page = webClient.getPage(bookInfoUrl);
            //4.将页面转成指定格式
            webClient.waitForBackgroundJavaScript(10000);   //等侍js脚本执行完成
            System.out.println("11112222"+page.asXml().getClass().getName());
            String entity = page.asXml();
            Document bookInfoDoc = Jsoup.parse(entity);
            Elements bookDetailDesc = bookInfoDoc.select("#detail_all");
            Elements keys = bookDetailDesc.select("ul[class=key clearfix]").select("li");
            bookSize = keys.get(0).text();
            bookSize = bookSize.substring(bookSize.indexOf("：") + 1);
            bookPackage = keys.get(2).text();
            bookPackage = bookPackage.substring(bookPackage.indexOf("：") + 1);
            ISBN = keys.get(4).text();
            ISBN = ISBN.substring(ISBN.indexOf("：") + 1);

            Elements select = bookDetailDesc.select("#detail").select("#catalog-show");

            catalog = select.text();

            StackRoom book = new StackRoom();
            book.setStackBookName(bookName);
            book.setStackBookImageUrl(imgUrl);
            book.setStackBookOutline(outline);
            book.setStackBookAuthor(author);
            book.setStackBookTypeId(Integer.parseInt(category));
            String substring = discount.substring(
                    (discount.indexOf("(") + 1) < 0 ? 0 : discount.indexOf("(") + 1, discount.indexOf(")") - 1 < 0 ? 0 : discount.indexOf(")") - 1);
            book.setStackBookDiscount(BigDecimal.valueOf(
                    Double.valueOf(
                            StringUtils.isEmpty(substring) ? "0" : substring
                    )
                    )
            );
            book.setStackBookPrice(BigDecimal.valueOf(Double.valueOf(bookRobPrice)));
            book.setStackBookMarketPrice(BigDecimal.valueOf(Double.valueOf(bookMarketPrice)));
            book.setStackBookPress(press);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(publishDate);
            book.setStackBookPublishDate(date);
            book.setStackBookSize(bookSize);
            book.setStackBookPackStyle(bookPackage);
            book.setStackBookIsbn(ISBN);
            book.setStackBookCatalog(catalog);
            book.setStackBookDetail(detail);
            book.setStackBookShopId(1);
            book.setStackBookShopTime(new Date());
            book.setStackBookDealMount(0);
            book.setStackBookLookMount(0);
            book.setStackBookShopMount(200);

            datas.add(book);
            if (i++ == 30) {
                break;
            }
        }
        //5.关闭模拟的窗口
        webClient.close();
        return datas;
    }

    /**
     * 功能描述：抓取页面时并解析页面的js
     * @param url
     * @throws Exception
     */
    public void crawlPageWithAnalyseJs(String url) throws Exception{
        //1.创建连接client
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //2.设置连接的相关选项
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);  //需要解析js
        webClient.getOptions().setThrowExceptionOnScriptError(false);  //解析js出错时不抛异常
        webClient.getOptions().setTimeout(10000);  //超时时间  ms
        //3.抓取页面
        HtmlPage page = webClient.getPage(url);
        //4.将页面转成指定格式
        webClient.waitForBackgroundJavaScript(10000);   //等侍js脚本执行完成
        System.out.println(page.asXml());
        //5.关闭模拟的窗口
        webClient.close();
    }
}
