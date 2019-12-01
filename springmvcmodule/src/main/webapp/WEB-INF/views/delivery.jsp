<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Delivery</title>
    <link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css" />
</head>

<body bgcolor="<spring:theme code='background'/>">
    <%@ include file="header.jsp" %>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" />
        <br/>
        <br/> Change language: <a href="${contextPath}/delivery?mylocale=en">English </a> | <a href="${contextPath}/delivery?mylocale=ru">Rus </a>
        <br/>
        <br/> Change theme: <a href="${contextPath}/delivery?mytheme=theme1">Theme 1 </a> | <a href="${contextPath}/delivery?mytheme=theme2"> Theme 2 </a>
        <br/>
        <br/>
        <spring:message code="delivery.products" />
        <table id="table">
            <tr>
                <th>
                    <spring:message code="product.name" />
                </th>
                <th>
                    <spring:message code="product.image" />
                </th>
                <th>
                    <spring:message code="product.deliveryStatus" />
                </th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.name}</td>
                    <td><img src="data:image/jpeg;charset=utf-8;base64,${product.base64}" width="150" height="150" /></td>
                    <td>
                        <c:choose>
                            <c:when test="${product.deliveryStatus}">
                                <spring:message code="product.delivered" />
                                <br/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="product.notDelivered" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:set var="isAdmin" scope="session" value="${sessionScope.user.isAdmin}" />
                        <c:if test="${isAdmin}">
                            <form:form action="${contextPath}/changeDeliveryStatus" method="post" modelAttribute="changeDeliveryStatusForm">
                                <form:input path="id" type="hidden" value="${product.id}" />
                                <form:input path="name" type="hidden" value="${product.name}" />
                                <form:input path="image" type="hidden" value="${product.image}" />
                                <form:input path="deliveryStatus" type="hidden" value="${product.deliveryStatus}" />
                                <spring:message code="product.changeStatus" var="valSubmit"></spring:message>
                                <input class="button" type="submit" value="${valSubmit}" />
                            </form:form>
                        </c:if>
                    </td>
                    <td>
                        <a href="${contextPath}/downloadImage/${product.id}">Download image</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form:form action="${contextPath}/findGoods" method="post" modelAttribute="searchGoodsForm">
            <spring:message code="product.name" />
            <form:input path="name" />
            <spring:message code="product.findGoods" var="valSubmit"></spring:message>
            <input class="button" type="submit" value="${valSubmit}" />
        </form:form>
        <c:if test="${!isAdmin}">
            <a href="${contextPath}/sortDelivery/asc">Sort by name in ascending order </a>
            <br/>
            <a href="${contextPath}/sortDelivery/desc">Sort by name in descending order </a>
            <br/>
            <a href="${contextPath}/delivery">reset order </a>
        </c:if>
</body>

</html>
