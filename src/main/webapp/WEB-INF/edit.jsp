<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:index>
    <jsp:attribute name="head">
        <link href="<c:url value="../styles/landing.css" />" rel="stylesheet">
        <link href="<c:url value="../styles/edit.css" />" rel="stylesheet">
        <script src="../js/avatarUpload.js"></script>
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
        <script>
            function getDefaultAvatarUrl() {
                return "${requestScope.avatar}";
            }
        </script>

        <form id="editForm" class="${not empty requestScope.errorMessage ? 'error' : ''}" enctype="multipart/form-data"
              action="editRequest" method="post" onsubmit="return editProfileSubmit();">
            <div class="row">
                <div class="col-lg-4">
                    <img src="${requestScope.avatar}" alt="Аватар">
                </div>
                <div class="col-lg-8">
                    <label class="btn btn-default btn-file" style="margin-bottom: 10px">
                        Выберите новый аватар
                        <input type="file" onchange="previewFile();" accept="image/*" name="avatar"
                               style="display: none;">
                    </label>
                    <label for="password" class="sr-only">Пароль</label>
                    <input type="password" id="password" name="password" class="form-control"
                           placeholder="Пароль"
                           style="border-top-left-radius: 4px; border-top-right-radius: 4px">
                    <label for="repeatPassword" class="sr-only">Пароль</label>
                    <input type="password" id="repeatPassword" name="repeatPassword" class="form-control"
                           placeholder="Повторите Пароль">
                    <div id="form-error" class="alert alert-danger">
                        <c:if test="${not empty requestScope.errorMessage}">
                            <strong>${requestScope.errorMessage}</strong>
                        </c:if>
                    </div>
                    <input type="submit" class="btn btn-lg btn-primary btn-block" value="Сохранить">
                </div>
            </div>
        </form>
    </jsp:body>
</t:index>