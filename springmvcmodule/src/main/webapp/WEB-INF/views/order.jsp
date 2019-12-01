<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Order</title>
    <link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css" />
</head>

<body bgcolor="<spring:theme code='background'/>">
    <%@ include file="header.jsp" %>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />
        <br/>
        <br/> Change language: <a href="${contextPath}/order?mylocale=en">English </a> | <a href="${contextPath}/order?mylocale=ru">Rus </a>
        <br/>
        <br/> Change theme: <a href="${contextPath}/order?mytheme=theme1">Theme 1 </a> | <a href="${contextPath}/order?mytheme=theme2"> Theme 2 </a>
        <br/>
        <br/>
        <spring:message code="order.products" />
        <table>
            <tr>
                <th>
                    <spring:message code="product.name" />
                </th>
                <th>
                    <spring:message code="product.image" />
                </th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.name}</td>
                    <td><img src="data:image/jpeg;charset=utf-8;base64,${product.base64}" width="150" height="150" /></td>
                </tr>
            </c:forEach>
        </table>
        <form:form action="${contextPath}/addOrder" method="post">
            <spring:message code="user.addOrder" var="valSubmit"></spring:message>
            <input class="button" type="submit" value="${valSubmit}" />
        </form:form>
</body>

</html>