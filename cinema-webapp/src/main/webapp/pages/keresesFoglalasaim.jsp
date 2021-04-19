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

<form action="${pageContext.request.contextPath}/Foglalasaim_Controller" method="post">
    <fieldset>
        <legend>Keresés</legend>
        <label for ="mit"><b>Mit keresel:</b> <input type="text" id="mit" name="mit"></label><br>
        <label for ="miben"><b>Miben keresed:</b>
            <select id="miben" name="miben">
                <option value="datum">Időpont</option>
                <option value="jegyar">Jegyár</option>
            </select>
        </label><br>
        <input type="submit" value="Keresés">
    </fieldset>
</form>

<table>
    <tr>
        <th>Email-cim</th>
        <th>Foglalt székek</th>
        <th>Jegyár</th>
        <th>Időpont</th>
        <th>Törlés</th>
        <th>Módositás</th>
    </tr>
    <c:forEach var="item" items="${requestScope.sclist}">
        <tr class="foglalasaimAdatok">
            <td>${item.emailProperty().value}</td>
            <td>${item.szekekProperty().value}</td>
            <td>${item.jegyarProperty().value}</td>
            <td class="idopont">${item.datumProperty().value} ${item.napProperty().value}</td>
            <td><a class = "torles" href ="foglalasaim.jsp?value=${item.jegy_idProperty().value}"> Törlés </a></td>
            <td><a class = "modositas" href ="modositas.jsp?helyek=${item.szekekProperty().value}&screeningID=${item.vetites_idProperty().value}&jegyar=${item.jegyarProperty().value}"> Módosítás </a></td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
