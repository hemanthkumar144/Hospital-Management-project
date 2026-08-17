<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ward Details</title>
</head>

<body>

<h1>Ward Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Ward ID</th>
        <td>${ward.wardId}</td>
    </tr>

    <tr>
        <th>Ward Name</th>
        <td>${ward.wardName}</td>
    </tr>

    <tr>
        <th>Ward Type</th>
        <td>${ward.wardType}</td>
    </tr>

    <tr>
        <th>Bed Charge</th>
        <td>${ward.bedCharge}</td>
    </tr>

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${ward.active}">
                    Active
                </c:when>

                <c:otherwise>
                    Inactive
                </c:otherwise>

            </c:choose>

        </td>

    </tr>

</table>

<br>

<a href="${pageContext.request.contextPath}/admin/wards/edit/${ward.wardId}">
    Edit Ward
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/wards">
    Back to Ward List
</a>

</body>
</html>