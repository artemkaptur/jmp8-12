<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>login</title>
    <link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css" />
</head>

<body bgcolor="<spring:theme code='background'/>">
    <%@ include file="header.jsp" %>
        <br/>
        <br/>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" /> Change language: <a href="${contextPath}/purchase?mylocale=en">English </a> | <a href="${contextPath}/purchase?mylocale=ru">Rus </a>
        <br/>
        <br/> Change theme: <a href="${contextPath}/purchase?mytheme=theme1">Theme 1 </a> | <a href="${contextPath}/purchase?mytheme=theme2"> Theme 2 </a>
        <c:set var="isAdmin" scope="session" value="${sessionScope.user.isAdmin}" />
        <c:choose>
            <c:when test="${isAdmin}">
                <table>
                    <tr>
                        <th>
                            <spring:message code="product.name" />
                        </th>
                        <th>
                            <spring:message code="product.image" />
                        </th>
                        <th>
                            <spring:message code="purchase.update" />
                        </th>
                        <th>
                            <spring:message code="purchase.delete" />
                        </th>
                    </tr>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.name}</td>
                            <td><img src="data:image/jpeg;charset=utf-8;base64,${product.base64}" width="150" height="150" />
                            </td>
                            <td>
                                <form:form action="${contextPath}/updateProduct" method="post" modelAttribute="updateGoodForm" enctype="multipart/form-data">
                                    <form:input path="id" type="hidden" value="${product.id}" />
                                    <spring:message code="admin.addNewGoodName" />
                                    <form:input path="name" />
                                    <spring:message code="admin.addNewImage" />
                                    <input type="file" name="file" />
                                    <spring:message code="admin.updateGood" var="valSubmit"></spring:message>
                                    <input class="button" type="submit" value="${valSubmit}" />
                                </form:form>
                            </td>
                            <td>
                                <form:form action="${contextPath}/deleteProduct" method="post" modelAttribute="deleteGoodForm">
                                    <form:input path="id" type="hidden" value="${product.id}" />
                                    <spring:message code="admin.deleteGood" var="valSubmit"></spring:message>
                                    <input class="button" type="submit" value="${valSubmit}" />
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <form:form action="${contextPath}/addProduct" method="post" enctype="multipart/form-data">
                    <spring:message code="admin.addNewGoodName" />
                    <input type="text" name="name" id="name" />
                    <spring:message code="admin.addNewImage" />
                    <input type="file" name="file" />
                    <spring:message code="admin.addGood" var="valSubmit"></spring:message>
                    <input class="button" type="submit" value="${valSubmit}" />
                </form:form>
            </c:when>
            <c:otherwise>
                <form:form action="${contextPath}/addPurchase" method="post" modelAttribute="purchaseForm">
                    <table>
                        <tr>
                            <th>
                                <spring:message code="product.name" />
                            </th>
                            <th>
                                <spring:message code="product.image" />
                            </th>
                            <th>
                                <spring:message code="user.addToPurchase" />
                            </th>
                        </tr>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>${product.name}</td>
                                <td><img src="data:image/jpeg;charset=utf-8;base64,${product.base64}" width="150" height="150" />
                                </td>
                                <td>
                                    <input type="checkbox" name="idGoods" value="${product.id}" />
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <spring:message code="user.addPurchase" var="valSubmit"></spring:message>
                    <input class="button" type="submit" value="${valSubmit}" />
                </form:form>
            </c:otherwise>
        </c:choose>
        <ul class="pagination pagination-sm">
            <c:forEach var="i" begin="1" end="${pageCount}">
                <li class="page-item"><a class="page-link" href="${contextPath}/purchase/${i}">${i}</a></li>
            </c:forEach>
        </ul>
</body>

</html>