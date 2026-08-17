<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Add Doctor</title>
</head>

<body>

<h1>Add Doctor</h1>

<hr>

<c:if test="${not empty error}">

    <p>${error}</p>

</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/doctors/add">

    <label>Name:</label>
    <input type="text"
           name="name"
           required>

    <br><br>


    <label>Age:</label>
    <input type="number"
           name="age"
           required>

    <br><br>


    <label>Gender:</label>

    <select name="gender"
            required>

        <option value="">
            Select Gender
        </option>

        <option value="MALE">
            Male
        </option>

        <option value="FEMALE">
            Female
        </option>

        <option value="OTHER">
            Other
        </option>

    </select>

    <br><br>


    <label>Phone:</label>
    <input type="text"
           name="phone"
           required>

    <br><br>


    <label>Salary:</label>
    <input type="number"
           step="0.01"
           name="salary"
           required>

    <br><br>


    <label>Experience:</label>
    <input type="number"
           name="experience"
           required>

    <br><br>


    <label>Specialization:</label>
    <input type="text"
           name="specialization"
           required>

    <br><br>


    <label>Consultation Fee:</label>
    <input type="number"
           step="0.01"
           name="consultationFee"
           required>

    <br><br>


    <label>Department:</label>

    <select name="departmentId"
            required>

        <option value="">
            Select Department
        </option>

        <c:forEach var="department"
                   items="${departments}">

            <option value="${department.departmentId}">
                ${department.departmentName}
            </option>

        </c:forEach>

    </select>

    <br><br>


    <button type="submit">
        Add Doctor
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/doctors">
    Back to Doctor List
</a>

</body>

</html>