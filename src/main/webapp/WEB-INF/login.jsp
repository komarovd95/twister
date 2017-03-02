<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="../vendor/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="../styles/main.css" />" rel="stylesheet">
    <script src="<c:url value="../vendor/jquery-3.1.1.min.js" />"></script>
    <script src="<c:url value="../vendor/bootstrap.min.js" />"></script>
    <script src="<c:url value="../js/login-form.js" />"></script>
    <title>Войти в Twister</title>
</head>
<body>
    <div class="container" id="form-container">
        <form id="login-form" class="form-signin ${not empty requestScope.errorMessage ? 'error' : ''}"
              action="loginRequest?redirect=${requestScope.redirectUrl}" method="post"
              onsubmit="return validateForm('login-form');">
            <h2 class="form-signin-heading">Войти в Twister</h2>
            <label for="username" class="sr-only">Ваше имя</label>
            <input type="text" id="username" name="username" class="form-control"
                   placeholder="Ваше имя" required autofocus>
            <label for="password" class="sr-only">Пароль</label>
            <input type="password" id="password" name="password" class="form-control"
                   placeholder="Пароль" required>
            <div id="form-error" class="alert alert-danger">
                <c:if test="${not empty requestScope.errorMessage}">
                    <strong>${requestScope.errorMessage}</strong>
                </c:if>
            </div>
            <a href="<c:url value="/register"/>">Нет аккаунта?</a>
            <input type="submit" class="btn btn-lg btn-primary btn-block" value="Войти">
        </form>
    </div>
</body>
</html>
