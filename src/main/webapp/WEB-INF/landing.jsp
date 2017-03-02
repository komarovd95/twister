<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:index>
    <jsp:attribute name="head">
        <link href="<c:url value="../styles/landing.css" />" rel="stylesheet">
    </jsp:attribute>
    <jsp:attribute name="title">
        Twister
    </jsp:attribute>
    <jsp:attribute name="header">
        <li role="presentation" class="active">
            <a href="<c:url value="/login"/>">
                Войти
            </a>
        </li>
    </jsp:attribute>
    <jsp:body>
        <div class="jumbotron">
            <h1>Присоединяйся к Twister</h1>
            <p class="lead">
                Twister - совершенно новая возможность пообщаться со своими друзьями, где бы они не находились:
                в классе со старыми компьютерами или в столовой вместе с тараканами.
                Просто отправь сообщение в Twister и его никто не прочитает.
            </p>
            <p><a class="btn btn-lg btn-success" href="/register" role="button">Зарегистрироваться</a></p>
        </div>

        <div class="row marketing">
            <div class="col-lg-12" style="text-align: center">
                <h2>А какой сегодня ты?</h2>
            </div>
            <div class="col-lg-6">
                <h4>Современный</h4>
                <p>JSP - крайне современная технология, которая подходит только нам</p>

                <h4>Загадочный</h4>
                <p>Весь трафик шифруется в угоду Госпоже Я.<br>(Но это не точно)</p>
            </div>

            <div class="col-lg-6">
                <h4>Красивый</h4>
                <p>Ну куда же без Twitter Bootstrap?</p>

                <h4>Быстрый</h4>
                <p>Мы используем язык программирования Java, на котором не тормозит даже HelloWorld<br>(Но это не точно)</p>
            </div>
        </div>
    </jsp:body>
</t:index>
