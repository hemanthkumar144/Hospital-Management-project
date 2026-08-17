<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Medicine Manufacturers</title>
</head>

<body>

<h1>Distinct Medicine Manufacturers</h1>

<hr>

<c:choose>

    <c:when test="${empty manufacturers}">
        <p>No manufacturer data found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>S.No</th>
                <th>Manufacturer</th>
            </tr>

            <c:forEach var="manufacturer"
                       items="${manufacturers}"
                       varStatus="status">

                <tr>
                    <td>${status.count}</td>
                    <td>${manufacturer}</td>
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