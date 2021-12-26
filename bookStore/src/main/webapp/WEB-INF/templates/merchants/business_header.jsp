<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<script type="text/javascript">
    $(document).ready(function () {

        /**
         * 为导航栏绑定选择事件
         * 点击
         */
        $(".header-tabs li").removeClass("active");
        $("#${typeId==null?0:typeId}").addClass("active")


        //轮播图间隔时间
        $('.myCarousel').carousel({
            interval: 2000
        });

        /**
         * 为导航栏绑定选择事件
         * 点击添加active效果
         */
        $(".nav-tabs li").click(function () {
            $(".nav-tabs li").removeClass("active");
            $(this).addClass("active");
        });
    });

    function submitSearchForm() {
        var keywords = $("#keywords").val();
        if("" == keywords.trim()){
            $("#keywords").val("java");
        }

        $("#searchForm").submit();
    }
</script>

<html>
<!--
    作者：chenchao
    时间：2021-04-10
    描述：top
-->
<%--页面嵌入在运行时完成，子页面拥有独立的作用域--%>
<jsp:include page="business_top.jsp"/>
<!--
    作者：chenchao
    时间：2021-04-10
    描述：logo search
-->

<div class="container">
    <div class="row">
        <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 logo">
            <a href="user/index" target="_blank"><img src="${applicationScope.globalParameter.weblogo}" width="90%"
                                                      alt="cc.com"/></a>
        </div>
        <!-- col-lg-x:大屏幕 大桌面显示器 (≥1200px) col-md-X:中等屏幕 桌面显示器 (≥992px) col-sm- 小屏幕 平板 (≥768px) col-xs-x:超小屏幕 手机 (<768px)-->
        <div class="col-lg-6 col-md-6 col-xs-12 search">
            <form action="book/list" class="form-inline" id="searchForm" role="form" method="get">
                <div class="form-group">
                    <label class="sr-only" for="keywords">关键字</label>
                    <input type="text" class="form-control" id="keywords" name="keywords" value="${keywords}"size="55"
                           placeholder="java">
                </div>
                <!--对内容进行着色-->
                <button type="button" onclick="submitSearchForm()" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span>
                    搜索
                </button>
            </form>
        </div>
        <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 search">
            <button type="button" onclick="window.open('cart/items')"
                    style="background-color: #ff2832;border-color: #ff2832" class="btn btn-info">
                <span class="glyphicon glyphicon-shopping-cart"></span>
                我的购物车
            </button>
            <button type="button" onclick="window.open('order/list')"
                    style="background-color: #ff2832;border-color: #ff2832" class="btn btn-info">我的订单
            </button>
        </div>
    </div>
    <div class="row">
        <ul class="nav nav-tabs header-tabs">

            <li class="active" id="0"><a href="">首页</a></li>
            <c:forEach items="${BT}" var="bookType">
                <li id="${bookType.getBookTypeId()}"><a href="index/Type/${bookType.getBookTypeId()}">${bookType.getBookTypeName()}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>
<div style="height: 3px; background-color: #ff2832;"></div>
</html>
