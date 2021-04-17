<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cinema</title>
    <link href="../css/mainstyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="common/common-menu.jsp"/>
<jsp:include page="/Movie_Controller"/>
<div id = "movies">
    <c:forEach var="item" items="${requestScope.movieList}">
            <a href = 'screening.jsp?value=${item.film_nevProperty().value}'><img src='${item.boritokepProperty().value}' title='${item.film_nevProperty().value}'></a>
    </c:forEach>
</div>
</body>
</html>