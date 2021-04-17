<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="../css/index.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src ="../js/script.js"></script>
    <title>Cinema</title>
</head>
<body>
<jsp:include page="/Login_Controller"/>

<div id = "left-section">
    <h1>Cinema</h1>
    <h2>The best cinema around, login now!</h2>
</div>
<section class = "main">
    <c:if test="${requestScope.error == 0}">
        <script>
            alert("Bad credentials");
        </script>
    </c:if>
    <form id="loginform" action="${pageContext.request.contextPath}/Login_Controller" method="post">
        <input type="text" id="username" name="username" placeholder="Your username.." maxlength="30" required><br>
        <input type="password" id="password" name="password" placeholder="Your password.." maxlength="15" required><br>
        <input id = "login" type="submit" value="Login">
        <hr>
        <input type="button" id = "reg" value="New account">
    </form>
</section>

</body>
</html>

