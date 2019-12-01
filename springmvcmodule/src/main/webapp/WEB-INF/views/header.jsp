<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <title>Header</title>
    <link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css" />
</head>

<body>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <a href="${contextPath}/purchase">
        <spring:message code="header.purchase" /> </a> |
    <a href="${contextPath}/order">
        <spring:message code="header.order" />
    </a> |
    <a href="${contextPath}/delivery">
        <spring:message code="header.delivery" />
    </a>
</body>

</html>

