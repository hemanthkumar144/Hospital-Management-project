<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Department Details</title>
</head>

<body>

<h1>Department Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Department ID</th>
        <td>${department.departmentId}</td>
    </tr>

    <tr>
        <th>Department Name</th>
        <td>${department.departmentName}</td>
    </tr>

    <tr>
        <th>Location</th>
        <td>${department.location}</td>
    </tr>

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${department.active}">
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

<a href="${pageContext.request.contextPath}/admin/departments/edit/${department.departmentId}">
    Edit Department
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/departments">
    Back to Department List
</a>

</body>
</html>