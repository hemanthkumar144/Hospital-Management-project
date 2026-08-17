<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Patient Names</title>
</head>

<body>

<h1>Patient Names</h1>

<hr>

<c:choose>

    <c:when test="${empty names}">
        <p>No patients found.</p>
    </c:when>

    <c:otherwise>

        <h3>Patient Names</h3>

        <p>${names}</p>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>

</html>