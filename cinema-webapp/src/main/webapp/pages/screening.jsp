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
    <link href="../css/mainstyle.css" rel="stylesheet" type="text/css">
    <title>Cinema</title>
</head>
<body>
<jsp:include page="common/common-menu.jsp"/>

<jsp:include page="/Screening_Controller"/>

<fieldset>
    <legend>${requestScope.Movie.film_nevProperty().value}</legend>
    <label><b>Film leiras:</b> ${requestScope.Movie.leirasProperty().value}</label><br>
    <label><b>Film hossz:</b> ${requestScope.Movie.hosszProperty().value} perc</label> <br>
    <label><b>Film besorolás:</b> ${requestScope.Movie.korhatarProperty().value}</label> <br>
    <label><b>Film rendező:</b> ${requestScope.Movie.rendezoProperty().value}</label> <br>
    <label><b>Film szereplők:</b> ${requestScope.Movie.szereplokProperty().value}</label>
</fieldset>

<table>
    <tr>
        <th>Film_nev</th>
        <th>Idopont</th>
        <th>Jegyar</th>
        <th>Terem_nev</th>
        <th>Foglalas</th>
    </tr>
    <c:forEach var="item" items="${requestScope.screeningList}">
    <tr>
        <td>${item.film_nevProperty().value}</td>
        <td>${item.datumProperty().value} ${item.napProperty().value}</td>
        <td>${item.jegyarProperty().value}</td>
        <td>${item.terem_nevProperty().value}</td>
        <td><a href="foglalas.jsp?screeningID=${item.idProperty().value}&hallname=${item.terem_nevProperty().value}">Foglalok</a></td>

    </tr>
    </c:forEach>

</table>

</body>
</html>
