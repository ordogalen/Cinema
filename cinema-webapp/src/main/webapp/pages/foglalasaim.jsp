<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../css/mainstyle.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src ="../js/foglalasaim.js"></script>
    <title>Cinema</title>
</head>
<body>
<jsp:include page="common/common-menu.jsp"/>
<jsp:include page="/Foglalasaim_Controller"/>
<table>
    <tr>
        <th>Email cim</th>
        <th>Szekek</th>
        <th>Jegyar</th>
        <th>Idopont</th>
        <th>Torles</th>
        <th>Modositas</th>
    </tr>
    <c:forEach var="item" items="${requestScope.sclist}">
        <tr class="foglalasaimAdatok">
            <td>${item.emailProperty().value}</td>
            <td>${item.szekekProperty().value}</td>
            <td>${item.jegyarProperty().value}</td>
            <td class="idopont">${item.datumProperty().value} ${item.napProperty().value}</td>
            <td><a class = "torles" href ="foglalasaim.jsp?value=${item.jegy_idProperty().value}"> Torles </a></td>
            <td><a class = "modositas" href ="modositas.jsp?helyek=${item.szekekProperty().value}&screeningID=${item.vetites_idProperty().value}&jegyar=${item.jegyarProperty().value}"> Modosítas </a></td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
