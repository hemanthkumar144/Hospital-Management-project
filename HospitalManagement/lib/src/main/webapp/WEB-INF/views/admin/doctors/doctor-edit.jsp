<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Edit Doctor</title>
</head>

<body>

<h1>Edit Doctor</h1>

<hr>

<c:if test="${not empty error}">

    <p>${error}</p>

</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/doctors/edit">

    <input type="hidden"
           name="staffId"
           value="${doctor.staffId}">


    <label>Name:</label>

    <input type="text"
           name="name"
           value="${doctor.name}"
           required>

    <br><br>


    <label>Age:</label>

    <input type="number"
           name="age"
           value="${doctor.age}"
           required>

    <br><br>


    <label>Gender:</label>

    <select name="gender"
            required>

        <option value="MALE"
            ${doctor.gender == 'MALE' ? 'selected' : ''}>
            Male
        </option>

        <option value="FEMALE"
            ${doctor.gender == 'FEMALE' ? 'selected' : ''}>
            Female
        </option>

        <option value="OTHER"
            ${doctor.gender == 'OTHER' ? 'selected' : ''}>
            Other
        </option>

    </select>

    <br><br>


    <label>Phone:</label>

    <input type="text"
           name="phone"
           value="${doctor.phone}"
           required>

    <br><br>


    <label>Salary:</label>

    <input type="number"
           step="0.01"
           name="salary"
           value="${doctor.salary}"
           required>

    <br><br>


    <label>Experience:</label>

    <input type="number"
           name="experience"
           value="${doctor.experience}"
           required>

    <br><br>


    <label>Specialization:</label>

    <input type="text"
           name="specialization"
           value="${doctor.specialization}"
           required>

    <br><br>


    <label>Consultation Fee:</label>

    <input type="number"
           step="0.01"
           name="consultationFee"
           value="${doctor.consultationFee}"
           required>

    <br><br>


    <label>Department:</label>

    <select name="departmentId"
            required>

        <c:forEach var="department"
                   items="${departments}">

            <option value="${department.departmentId}"
                ${doctor.department.departmentId == department.departmentId
                    ? 'selected'
                    : ''}>

                ${department.departmentName}

            </option>

        </c:forEach>

    </select>

    <br><br>


    <button type="submit">
        Update Doctor
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/doctors">
    Back to Doctor List
</a>

</body>

</html>