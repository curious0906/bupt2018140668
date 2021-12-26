<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bs.css"/>
</head>
<style>
    #left-menu {
        font-size: 13px;
        line-height: 18px;
        color: #fff;
    }

    #left-menu-main {
    }

    .left-menu-main-list {
        background-color: #0A0A0A;
    }

    .panel-body li:hover {
        background-color: #222222;
    }

    .panel-body a {
        display: inline-block;
        width: 218px;
        color: #fff;
        font-size: 20px;
        margin-top: 10px;
    }
</style>
<body style="background-color: #0A0A0A;">

<div class="container" id="left-menu" style="padding-right:20px ;">

    <div class="row" id="center_header" style="height: 100px;border-bottom: 1px solid #CCCCCC;">
    </div>

    <div class="row" id="left-menu-main">
        <c:if test="${sessionScope.loginStore != null }">
                <div class="panel panel-default left-menu-main-list">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                <span class="glyphicon glyphicon-asterisk"></span> 我的店铺
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul>

                                <li>
                                    <a href="merchants/store/${sessionScope.loginStore.shopId}" target="centerFrame">店铺信息</a>
                                </li>
                                <li>
                                    <a href="merchants/book/list?page=1" target="centerFrame">我的图书</a>
                                </li>
                                <li>
                                    <a href="merchants/book/toAddition" target="centerFrame">图书上新</a>
                                </li>
                                <li>
                                    <a href="merchants/order/list" target="centerFrame">订单管理</a>
                                </li>
                                <li>
                                    <a href="merchants/return/info" target="centerFrame">退货管理</a>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>


                <div class="panel panel-default left-menu-main-list">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFive">
                                <span class="glyphicon glyphicon-list-alt"></span> 报表统计
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul>
                                <li>
                                    <a href="admin/report/views" target="centerFrame">访问量</a>
                                </li>
                                <li>
                                    <a href="admin/report/sales" target="centerFrame">销售量</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
        </c:if>

            <div class="panel panel-default left-menu-main-list">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapseSix">
                            <span class="glyphicon glyphicon-user"></span> 个人中心
                        </a>
                    </h4>
                </div>
                <div id="collapseSix" class="panel-collapse collapse">
                    <div class="panel-body">
                        <ul>
                            <li>
                                <a href="merchants/user/echo/${sessionScope.loginUser.userId}" target="centerFrame">我的信息</a>
                            </li>
                                <%--<li>
                                    <a href="admin/user/password" target="centerFrame">修改密码</a>
                                </li>--%>
                        </ul>
                    </div>
                </div>
            </div>

        <div class="panel panel-default left-menu-main-list">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#collapseSeven">
                        <span class="glyphicon glyphicon-home"></span> 前往
                    </a>
                </h4>
            </div>
            <div id="collapseSeven" class="panel-collapse collapse">
                <div class="panel-body">
                    <ul>
                        <li>
                            <a href="merchants_index" target="_blank">前台首页</a>
                        </li>
                        <li>
                            <a href="merchants/center" target="centerFrame">后台首页</a>
                        </li>
                        <li>
                            <a href="merchants/user/logout" target="_top">注销</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
