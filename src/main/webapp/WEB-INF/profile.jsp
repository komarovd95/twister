<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:index>
    <jsp:attribute name="head">
        <link href="<c:url value="../styles/landing.css" />" rel="stylesheet">
    </jsp:attribute>
    <jsp:attribute name="title">
        Twister: ${requestScope.userProfile.username}
    </jsp:attribute>
    <jsp:attribute name="header">
        <li role="presentation">
            <a href="<c:url value="/"/>">
                Лента
            </a>
        </li>
        <li role="presentation">
            <a href="#">
                Поиск
            </a>
        </li>
        <li role="presentation">
            <a href="<c:url value="/logout"/>">
                Выйти
            </a>
        </li>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-lg-3 sidebar">
                <img src="${requestScope.avatar}" alt="Avatar"/>
                <h4>${requestScope.userProfile.username}</h4>
                <div class="list-group">
                    <a href="<c:url value="/edit"/>" class="list-group-item">Редактировать</a>
                    <a href="<c:url value="/logout"/>" class="list-group-item">Выйти</a>
                </div>
            </div>
            <div class="col-lg-9 posts-feed">
                <form class="clearfix">
                    <h4>Новое сообщение</h4>
                    <textarea name="text" placeholder="Текст Вашего сообщения (не более 140 символов)" maxlength="140">
                </textarea>
                    <input type="submit" class="btn btn-primary btn-block" value="Опубликовать">
                </form>
                <div class="posts-list">
                    <h4>Мои сообщения</h4>
                    <div class="posts-list-item">
                        <p>Тестовый пост</p>
                    </div>
                    <div class="posts-list-item">
                        <p>Тестовый пост</p>
                    </div>
                    <div class="posts-list-item" onclick="console.log('clicked')">
                        <p>Тестовый пост</p>
                        <div class="row">
                            <div class="col-lg-6">
                                Автор: <a href="#">dimasik1337</a>
                            </div>
                            <div class="col-lg-6" style="text-align: right">
                                7 комментариев
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                Дата: 08.02.2017 08:33:07
                            </div>
                            <div class="col-lg-6" style="text-align: right">
                                Понравилось 8 людям
                            </div>
                        </div>
                            <%--<div class="post-info">--%>
                            <%--<div class="author">--%>
                            <%--Автор: <a href="#">dimasik1337</a>--%>
                            <%--</div>--%>
                            <%--<div class="date">--%>
                            <%--Дата: 08.02.2017 08:33:07--%>
                            <%--</div>--%>
                            <%--</div>--%>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:index>
