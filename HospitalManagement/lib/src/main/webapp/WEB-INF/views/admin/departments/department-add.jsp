<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Department</title>
</head>

<body>

<h1>Add Department</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/departments/add">

    <label>Department Name:</label>

    <input type="text"
           name="departmentName"
           required>

    <br><br>

    <label>Location:</label>

    <input type="text"
           name="location"
           required>

    <br><br>

    <button type="submit">
        Add Department
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/departments">
    Back to Department List
</a>

</body>
</html>