<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Nurse</title>
</head>

<body>

<h1>Edit Nurse</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/nurses/edit">

    <input type="hidden"
           name="staffId"
           value="${nurse.staffId}">


    <label>Name:</label>

    <input type="text"
           name="name"
           value="${nurse.name}"
           required>

    <br><br>


    <label>Age:</label>

    <input type="number"
           name="age"
           min="18"
           max="65"
           value="${nurse.age}"
           required>

    <br><br>


    <label>Gender:</label>

    <select name="gender" required>

        <option value="MALE"
            ${nurse.gender == 'MALE' ? 'selected' : ''}>
            Male
        </option>

        <option value="FEMALE"
            ${nurse.gender == 'FEMALE' ? 'selected' : ''}>
            Female
        </option>

        <option value="OTHER"
            ${nurse.gender == 'OTHER' ? 'selected' : ''}>
            Other
        </option>

    </select>

    <br><br>


    <label>Phone:</label>

    <input type="text"
           name="phone"
           value="${nurse.phone}"
           maxlength="10"
           required>

    <br><br>


    <label>Salary:</label>

    <input type="number"
           name="salary"
           step="0.01"
           value="${nurse.salary}"
           required>

    <br><br>


    <label>Department:</label>

    <select name="departmentId" required>

        <c:forEach var="department"
                   items="${departments}">

            <option value="${department.departmentId}"
                ${nurse.department.departmentId
                    == department.departmentId
                    ? 'selected'
                    : ''}>

                ${department.departmentName}

            </option>

        </c:forEach>

    </select>

    <br><br>


    <label>Shift:</label>

    <select name="shift" required>

        <option value="MORNING"
            ${nurse.shift == 'MORNING'
                ? 'selected'
                : ''}>
            Morning
        </option>

        <option value="EVENING"
            ${nurse.shift == 'EVENING'
                ? 'selected'
                : ''}>
            Evening
        </option>

        <option value="NIGHT"
            ${nurse.shift == 'NIGHT'
                ? 'selected'
                : ''}>
            Night
        </option>

        <option value="ROTATIONAL"
            ${nurse.shift == 'ROTATIONAL'
                ? 'selected'
                : ''}>
            Rotational
        </option>

    </select>

    <br><br>


    <button type="submit">
        Update Nurse
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/nurses">
    Back to Nurse List
</a>

</body>
</html>