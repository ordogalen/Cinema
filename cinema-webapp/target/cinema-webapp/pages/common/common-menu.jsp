<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar">
    <ul>
        <li><a class="active" href="#home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/pages/filmek.jsp">Movies</a></li>
        <li><a href="${pageContext.request.contextPath}/pages/foglalasaim.jsp">My reservations</a></li>
        <c:if test="${sessionScope.currentUser.nev != null}">
            <li id = "registration"><a href="${pageContext.request.contextPath}/Logout_Controller">${sessionScope.currentUser.nev}</a></li>
        </c:if>
    </ul>
</nav>
