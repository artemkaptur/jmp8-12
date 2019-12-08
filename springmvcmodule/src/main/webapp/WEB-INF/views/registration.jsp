<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Registration</title>
    <link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css" />
</head>

<body bgcolor="<spring:theme code='background'/>">
    <c:set var="contextPath" value="${pageContext.request.contextPath}" /> Change language: <a href="${contextPath}/registration?mylocale=en">English </a> | <a href="${contextPath}/registration?mylocale=ru">Rus </a>
    <br/>
    <br/> Change theme: <a href="${contextPath}/registration?mytheme=theme1">Theme 1 </a> | <a href="${contextPath}/registration?mytheme=theme2"> Theme 2 </a>
    <form:form action="${contextPath}/registration" method="post" modelAttribute="user">
        <spring:message code="user.login" />
        <form:input path="login" />
        <br>
        <form:errors path="login" />
        <br>
        <spring:message code="user.password" />
        <form:input path="password" />
        <br>
        <form:errors path="password" />
        <br>
        <spring:message code="user.role" />
        <form:checkbox path="isAdmin" />
        <spring:message code="user.submit" var="valSubmit"></spring:message>
        <input class="button" type="submit" value="${valSubmit}" />
    </form:form>
</body>

</html>
