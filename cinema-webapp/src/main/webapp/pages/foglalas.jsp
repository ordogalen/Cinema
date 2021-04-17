<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: emese
  Date: 2021.04.09.
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../css/mainstyle.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src ="../js/foglalas.js"></script>
    <title>Cinema</title>
</head>
<body>
<jsp:include page="common/common-menu.jsp"/>
<jsp:include page="/Foglalas_Controller"/>

<div id="chairArea">
    <c:forEach var = "i" begin = "0" end = "${requestScope.sor*requestScope.oszlop-1}">
        <c:if test="${i % requestScope.sor == 0}">
            <br>
        </c:if>
        <c:choose>
            <c:when test="${requestScope.szekek[i] == 1}">
                <span class = "reservedChair">${i}</span>
            </c:when>
            <c:otherwise>
                <span class = "chair">${i}</span>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<button class="open-button" onclick="openFoglalas()">Kinyit</button>

<div class="form-popup" id="foglalas-container">
    <form action="${pageContext.request.contextPath}/Foglalas_Controller" method="post" class ="form-container">
        <label for="email">Email: </label><input type="text" id="email" value="${requestScope.email}" name="email" readonly="readonly"><br>
        <label for = "idopont">Datum: </label><input type="text" id="idopont" name="idopont" readonly="readonly" value="${requestScope.idopont}"><br>
        <label for="jegyar">Jegyar: </label><input type="text" id="jegyar"  name = "jegyar" value="${requestScope.jegyar}"  readonly="readonly"><br>
        <input type="text" id="vetites_id" name="vetites_id" value="${requestScope.vetites_id}" hidden />
        <input type="text" id="helyek" name="helyek" required hidden readonly="readonly"><br>
        <input id = "foglal" class="btn" type="submit" value="Foglal">
        <button type="button" class="btn cancel" onclick="closeFoglalas()">Osszecsuk</button>

    </form>
</div>
<script>
    function openFoglalas() {
        document.getElementById("foglalas-container").style.display = "block";
    }

    function closeFoglalas() {
        document.getElementById("foglalas-container").style.display = "none";
    }
</script>
</body>
</html>
