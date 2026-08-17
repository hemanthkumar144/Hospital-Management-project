<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nurse Details</title>
</head>

<body>

<h1>Nurse Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Nurse ID</th>
        <td>${nurse.staffId}</td>
    </tr>

    <tr>
        <th>Name</th>
        <td>${nurse.name}</td>
    </tr>

    <tr>
        <th>Age</th>
        <td>${nurse.age}</td>
    </tr>

    <tr>
        <th>Gender</th>
        <td>${nurse.gender}</td>
    </tr>

    <tr>
        <th>Phone</th>
        <td>${nurse.phone}</td>
    </tr>

    <tr>
        <th>Salary</th>
        <td>${nurse.salary}</td>
    </tr>

    <tr>
        <th>Department</th>
        <td>${nurse.department.departmentName}</td>
    </tr>

    <tr>
        <th>Shift</th>
        <td>${nurse.shift}</td>
    </tr>

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${nurse.active}">
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

<a href="${pageContext.request.contextPath}/admin/nurses/edit/${nurse.staffId}">
    Edit Nurse
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/nurses">
    Back to Nurse List
</a>

</body>
</html>