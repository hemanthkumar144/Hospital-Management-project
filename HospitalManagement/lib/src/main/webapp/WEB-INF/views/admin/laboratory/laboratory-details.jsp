<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Laboratory Test Details</title>
</head>

<body>

<h1>Laboratory Test Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Test ID</th>
        <td>${test.testId}</td>
    </tr>

    <tr>
        <th>Test Name</th>
        <td>${test.testName}</td>
    </tr>

    <tr>
        <th>Description</th>
        <td>${test.testDescription}</td>
    </tr>

    <tr>
        <th>Charge</th>
        <td>${test.testCharge}</td>
    </tr>

    <tr>
        <th>Test Type</th>
        <td>${test.testType}</td>
    </tr>

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${test.active}">
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

<a href="${pageContext.request.contextPath}/admin/laboratory/edit/${test.testId}">
    Edit Test
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/laboratory">
    Back to Laboratory List
</a>

</body>
</html>