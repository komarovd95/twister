<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:index>
    <jsp:attribute name="head">
        <link href="<c:url value="../styles/landing.css" />" rel="stylesheet">
        <script src="<c:url value="../js/profile.js"/>"></script>
    </jsp:attribute>
    <jsp:attribute name="title">
        Twister: ${requestScope.userProfile.username}
    </jsp:attribute>
    <jsp:attribute name="header">
        <li role="presentation">
            <a href="<c:url value="/logout"/>">
                Выйти
            </a>
        </li>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="col-lg-3 sidebar">
                <div class="square">
                    <div class="crop">
                        <img src="${requestScope.avatar}" alt="Avatar"/>
                    </div>
                </div>
                <h4>${requestScope.userProfile.username}</h4>
                <c:if test="${requestScope.itsMe}">
                    <div class="list-group">
                        <a href="<c:url value="/edit"/>" class="list-group-item">
                            Редактировать
                        </a>
                        <a href="<c:url value="/logout"/>" class="list-group-item">
                            Выйти
                        </a>
                    </div>
                </c:if>
            </div>
            <div class="col-lg-9 posts-feed">
                <c:if test="${requestScope.itsMe}">
                    <form class="clearfix" action="posts" method="post">
                        <h4>Новое сообщение</h4>
                        <textarea name="text" placeholder="Текст Вашего сообщения (не более 140 символов)"
                                  maxlength="140" onkeyup="postTextChange();" onchange="postTextChange();"></textarea>
                        <input type="submit" class="btn btn-primary btn-block" value="Опубликовать" disabled>
                    </form>
                </c:if>
                <div class="posts-list">
                    <h4>Сообщения от ${requestScope.userProfile.username}</h4>
                    <c:if test="${empty requestScope.posts}">
                        <div class="posts-list-item">
                            <p>Нет сообщений</p>
                        </div>
                    </c:if>
                    <c:forEach items="${requestScope.posts}" var="post">
                        <div class="posts-list-item">
                            <p>${post.text}</p>
                            <div class="row">
                                <div class="col-lg-6"></div>
                                <div class="col-lg-6" style="text-align:right">
                                    От <a href="<c:url value="/${post.user.username}"/>">${post.user.username}</a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">
                                    <a href="<c:url value="/posts/${post.id}"/>">
                                        <fmt:formatDate value="${post.date}" pattern="dd/MM/yyyy HH:mm:ss" />
                                    </a>
                                </div>
                                <div class="col-lg-6" style="text-align: right">
                                    <a class="${post.likedByMe ? 'liked' : 'unliked'}"
                                       href="<c:url value="${post.likedByMe ? '/unlike' : '/like'}?redirectTo=/${requestScope.userProfile.username}&postId=${post.id}" />">
                                        &#10084; ${post.likesCount}
                                    </a>
                                    &#128172; ${post.commentsCount}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </jsp:body>
</t:index>
