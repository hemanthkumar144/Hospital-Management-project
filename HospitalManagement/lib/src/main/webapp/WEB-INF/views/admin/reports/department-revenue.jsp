<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Department Revenue</title>
</head>

<body>

<h1>Department Revenue</h1>

<hr>

<c:choose>

    <c:when test="${empty revenue}">
        <p>No revenue data found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Department</th>
                <th>Total Revenue</th>
            </tr>

            <c:forEach var="entry"
                       items="${revenue}">

                <tr>
                    <td>${entry.key}</td>
                    <td>${entry.value}</td>
                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>