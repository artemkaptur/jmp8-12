<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>searchResult</title>
    <link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css" />
</head>

<body>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <%@ include file="header.jsp" %>
        <br/>
        <br/>
        <c:choose>
            <c:when test="${isExist}">
                ${searchedProduct.name}
                <br/>
                <img src="data:image/jpeg;charset=utf-8;base64,${searchedProduct.base64}" width="150" height="150" />
            </c:when>
            <c:otherwise>
                ${searchResultMessage}
            </c:otherwise>
        </c:choose>
        <br/>
        <a href="${contextPath}/delivery">back</a>
</body>

</html>
