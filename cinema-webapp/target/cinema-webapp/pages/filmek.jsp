<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="../css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="common/common-menu.jsp"/>
<jsp:include page="/Movie_Controller"/>
<div id = "movies">
    <c:forEach var="item" items="${requestScope.movieList}">
    <%--    <tr>--%>
    <%--        <td>${item.film_nevProperty().value}</td>--%>
    <%--        <td>${item.korhatarProperty().value}</td>--%>
    <%--        <td>${item.leirasProperty().value}</td>--%>
    <%--        <td>${item.rendezoProperty().value}</td>--%>
    <%--        <td>${item.szereplokProperty().value}</td>--%>
    <%--        <td>${item.hosszProperty().value}</td>--%>
            <a href = 'screening.jsp?value=${item.film_nevProperty().value}'><img src='${item.boritokepProperty().value}' height='200px' width='350px' title='${item.film_nevProperty().value}'></a>
        </tr>
    </c:forEach>
</div>
</body>
</html>