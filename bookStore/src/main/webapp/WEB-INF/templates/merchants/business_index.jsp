<%--这是导入jstl（jsp标准标记库）的语句，导入之后，就可以使用jstl中的标签了，如：<c:set>,<c:out>等等
    核心标签、格式化标签、SQL 标签、XML 标签、JSTL 函数--%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%--决定了浏览器到服务器发送时使用的编码；以及服务器返回到浏览器使用的编码。设定了页面的语言为jsp。--%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%--这三句话的话就可以直接写到绝对路径，但是有的话就要写相对路径--%>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path+"/";
    %>

    <%--文档类型声明为HTML--%>
    <!DOCTYPE html>
    <%--标签限定了文档的开始点和结束点--%>
    <html>
    <%--元素包含了所有的头部标签元素。在 <head>元素中你可以插入脚本（scripts）, 样式文件（CSS），及各种 meta 信息。--%>
        <head>
            <%--告知浏览器此页面属于什么字符编码格式--%>
            <meta charset="utf-8"/>
            <%--该meta标签的作用是让当前viewport的宽度等于设备的宽度，原始放缩比为1--%>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <%--web网页名称--%>
            <title>${applicationScope.webParameter.web_name}</title>
            <!--链接常用的前端框架
                导入jquery前端框架
                导入bootstrap前端框架
                导入bs前端框架
                供body调用-->
            <%--规定页面中所有相对链接的基准URL--%>
            <base href="<%=basePath%>">
            <%--<script type="text/javascript"></script>js脚本导入jquery和bootstrap库--%>
            <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
            <script type="text/javascript" src="js/bootstrap.min.js"></script>
            <%--给网页添加一个小图标--%>
            <link rel="shortcut icon" href="img/java.ico" type="image/x-icon"/>
            <%--链接两个样式表通过class使用它可对网页进行整体布局，字体、颜色很多细节控制--%>
            <link rel="stylesheet" href="css/bootstrap.min.css">
            <link rel="stylesheet" href="css/bs.css"/>

        </head>
        <%--页面主题部分--%>
    <body>
    <%--页面嵌入在运行时完成，子页面拥有独立的作用域--%>
    <jsp:include page="business_header.jsp"/>
    <%--class="container"调用bootstrap.css .container样式确定布局。style=margin-top设置元素的上外边距--%>
    <div class="container" style="margin-top:10px ;">
        <%--宽度固定，高度height可滚动--%>
        <div class="row" style="height: 850px;">
            <!-- col-lg-x:大屏幕 大桌面显示器 (≥1200px) col-md-X:中等屏幕 桌面显示器 (≥992px) col-sm- 小屏幕 平板 (≥768px)
            col-xs-x:超小屏幕 手机 (<768px) sort样式表bs.css-->
            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 sort" style="width: 17.5%;height: 100%;padding: 0;">
                <%-- sort_header、sort_ul、sort_li、sort_span样式bs.css--%>
                <div id="sort_header">
                    <span>图书分类</span>
                </div>
                <ul id="sort_ul">
                    <li class="sort_li">
                        <a href="">特色书单</a>
                        <span class="sort_span">></span>
                    </li>
                    <c:forEach items="${BT}" var="bookTypeObject">
                        <li class="sort_li">
                            <a href="book/list?typeId=${bookTypeObject.getBookTypeId()}">${bookTypeObject.getBookTypeName()}</a>
                            <span class="sort_span">></span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <!-- col-lg-x:大屏幕 大桌面显示器 (≥1200px) col-md-X:中等屏幕 桌面显示器 (≥992px) col-sm- 小屏幕 平板 (≥768px)
            col-xs-x:超小屏幕 手机 (<768px) carousel轮播组件 bootstrap.js-->
            <div class="col-lg-6 col-md-4 col-sm-6 col-xs-12" style="width: 65%;height: 100%;">
                <%--包括carousel、slide、carousel-indicators、.carousel-indicators .active、--%>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <%-- active bootstrap.css--%>
                        <li data-target="#myCarousel" data-slide-to="0"
                            class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                        <li data-target="#myCarousel" data-slide-to="3"></li>
                        <li data-target="#myCarousel" data-slide-to="4"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 750x315-->
                    <%--carousel-inner、.carousel-inner > .item、
                    .carousel-inner > .item > img、
                    .carousel-inner > .item > a > img、--%>
                    <div class="carousel-inner">
                        <div class="item active">
                            <img src="img/lunbo6.jpg" alt="First slide">
                        </div>
                        <div class="item">
                            <img src="img/lunbo2.jpg" alt="Second slide">
                        </div>
                        <div class="item">
                            <img src="img/lunbo3.jpg" alt="Third slide">
                        </div>
                        <div class="item">
                            <img src="img/lunbo4.jpg" alt="Third slide">
                        </div>
                        <div class="item">
                            <img src="img/lunbo5.jpg" alt="Third slide">
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <%-- .carousel-control.left、
                    .glyphicon、.sr-only 、
                    .carousel-control.right、
                    .carousel-control .glyphicon-chevron-left、
                    .carousel-control .glyphicon-chevron-right
                    role=button <a></a>实际是button--%>
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
                <%--.index_product_top、.title、.div_hr、.product_div bs.css组件--%>
                <div class="index_product_top">
                    <span class="title">新书上架</span>
                    <div class="div_hr"></div>
                </div>
                <div class="product_div">
                    <ul class="product_ul">

                        <c:forEach items="${bookDetails}" begin="0" end="7" var="bookInfo">
                            <%--.product_ul li样式、--%>
                            <li class="product_li">
                                <a href="book/info/${bookInfo.getBookId()}" class="img" target="_blank">
                                    <%-- .product_li img 样式--%>
                                    <img src="${bookInfo.getBookImageUrl()}"/>
                                </a>
                                <%--.name--%>
                                <p class="name">
                                    <a href="book/info/${bookInfo.getBookId()}">${bookInfo.getBookName()}</a>
                                </p>
                                <%--.author --%>
                                <p class="author">${bookInfo.getBookAuthor()}</p>
                                <%--.price --%>
                                <p class="price">
                                    <%--.rob--%>
                                    <span class="rob">￥${bookInfo.getBookPrice()}</span>
                                    <%--.oprice --%>
                                    <span class="oprice">￥${bookInfo.getBookMarketPrice()}</span>
                                </p>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <%-- col-lg-x:大屏幕 大桌面显示器 (≥1200px) col-md-X:中等屏幕 桌面显示器 (≥992px) col-sm- 小屏幕 平板 (≥768px)
            col-xs-x:超小屏幕 手机 (<768px)  bs.css--%>
            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12" style="width: 17.5%;height: 100%; padding: 0;">
                <%--.news、.news li--%>
                <div class="news">
                    <%--.title--%>
                    <p class="title">最新动态</p>
                    <ul>
                        <li>要买好书，就来东东</li>
                        <li>今日特惠，5折抢购</li>
                        <li>满100减50</li>
                        <li>满300减150</li>
                        <li>满400减210</li>
                        <li><%=basePath%></li>
                        <%--http://localhost:8080/mvcDemo_master_war_exploded/--%>

                    </ul>
                </div>
                <%--.hot_book--%>
                <div class="hot_book">
                    <p class="title">新书热卖</p>
                    <ul>
                        <li>要买好书，就来迟迟</li>
                        <li>今日特惠，5折抢购</li>
                        <li>满100减50</li>
                        <li>满300减150</li>
                        <li>满400减210</li>
                    </ul>
                </div>
            </div>
        </div>
        <%--.row bootstrap.css--%>
        <div class="row" style="margin-top: 30px;height: 500px;">
            <%-- col-lg-x:大屏幕 大桌面显示器 (≥1200px) col-md-X:中等屏幕 桌面显示器 (≥992px) col-sm- 小屏幕 平板 (≥768px)
            col-xs-x:超小屏幕 手机 (<768px)  bs.css--%>
            <div class="col-lg-10 col-md-12 col-sm-12 col-xs-12" style="width: 82.5%;padding-left: 0;">
                <%--.index_product_top样式表、.title、.div_hr、--%>
                <div class="index_product_top">
                    <span class="title">好书推荐</span>
                    <div class="div_hr"></div>
                </div>
                <%--.product_div样式表--%>
                <div class="product_div">
                    <%-- .product_ul li、.product_li img--%>
                    <ul class="product_ul">
                        <c:forEach items="${bookDetails}" begin="8" end="17" var="bookInfo">
                            <li class="product_li">
                                <a href="book/info/${bookInfo.getBookId()}" class="img" target="_blank">
                                    <img src="${bookInfo.getBookImageUrl()}"/>
                                </a>
                                <%--.name、.author、.price、.rob、.oprice--%>
                                <p class="name">
                                    <a href="book/info/${bookInfo.getBookId()}">${bookInfo.getBookName()}</a>
                                </p>
                                <p class="author">${bookInfo.getBookAuthor()}</p>
                                <p class="price">
                                    <span class="rob">￥${bookInfo.getBookPrice()}</span>
                                    <span class="oprice">￥${bookInfo.getBookMarketPrice()}</span>
                                </p>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <%-- col-lg-x:大屏幕 大桌面显示器 (≥1200px) col-md-X:中等屏幕 桌面显示器 (≥992px) col-sm- 小屏幕 平板 (≥768px)
            col-xs-x:超小屏幕 手机 (<768px)  bs.css--%>
            <div class="col-lg-2 col-md-12 col-sm-12 col-xs-12" style="width: 17.5%;padding: 0;padding-top:20px ;">
                <%--.hot_book、.title、--%>
                <div class="hot_book">
                    <p class="title">畅销图书</p>
                    <ul>
                        <li>要买好书，就来迟迟</li>
                        <li>今日特惠，5折抢购</li>
                        <li>满100减50</li>
                        <li>满300减150</li>
                        <li>满400减210</li>
                        <%--                    <li><%=basePath%></li>--%>
                    </ul>
                </div>
            </div>
        </div>

    </div>
    </div>

    <div style="height: 3px; background-color: #ff2832;"></div>

    <%--<jsp:include page="footer.jsp"/>--%>
    </body>

    </html>