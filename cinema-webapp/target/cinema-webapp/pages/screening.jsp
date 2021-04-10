<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: emese
  Date: 2021.04.09.
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../css/main.css" rel="stylesheet" type="text/css">

    <title>Title</title>
</head>
<body>
<jsp:include page="common/common-menu.jsp"/>

<jsp:include page="/Screening_Controller"/>
<table>
    <tr>
        <td>Film_nev</td>
        <td>Idopont</td>
        <td>Jegyar</td>
        <td>Terem_nev</td>
        <td>Foglalas</td>
    </tr>
    <c:forEach var="item" items="${requestScope.screeningList}">
    <tr>
        <td>${item.film_nevProperty().value}</td>
        <td>${item.datumProperty().value}</td>
        <td>${item.jegyarProperty().value}</td>
        <td>${item.terem_nevProperty().value}</td>
        <td><a href="foglalas.jsp?value=${item.idProperty().value}&hallname=${item.terem_nevProperty().value}">Foglalok</a></td>
    </tr>
    </c:forEach>

</table>
</body>
</html>
