<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" fragment="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="header" fragment="true" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="<c:url value="../../vendor/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="../../styles/main.css" />" rel="stylesheet">
    <script src="<c:url value="../../vendor/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="../../vendor/bootstrap.min.js" />"></script>
    <jsp:invoke fragment="head"/>
    <title><jsp:invoke fragment="title"/></title>
</head>
<body>
<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <jsp:invoke fragment="header"/>
            </ul>
        </nav>
        <h3>
            <a href="<c:url value="/"/>">
                Twister
            </a>
        </h3>
    </div>

    <jsp:doBody/>

    <footer class="footer">
        <p>&copy; 2017 Самарский Университет, Самара</p>
    </footer>
</div>
</body>
</html>