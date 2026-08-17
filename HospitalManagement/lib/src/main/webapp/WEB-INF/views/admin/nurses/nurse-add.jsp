<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Nurse</title>
</head>

<body>

<h1>Add Nurse</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/nurses/add">

    <label>Name:</label>
    <input type="text"
           name="name"
           required>

    <br><br>

    <label>Age:</label>
    <input type="number"
           name="age"
           min="18"
           max="65"
           required>

    <br><br>

    <label>Gender:</label>

    <select name="gender" required>

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
           maxlength="10"
           required>

    <br><br>

    <label>Salary:</label>
    <input type="number"
           name="salary"
           step="0.01"
           required>

    <br><br>

    <label>Department:</label>

    <select name="departmentId" required>

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

    <label>Shift:</label>

    <select name="shift" required>

        <option value="">
            Select Shift
        </option>

        <option value="MORNING">
            Morning
        </option>

        <option value="EVENING">
            Evening
        </option>

        <option value="NIGHT">
            Night
        </option>

        <option value="ROTATIONAL">
            Rotational
        </option>

    </select>

    <br><br>

    <button type="submit">
        Add Nurse
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/nurses">
    Back to Nurse List
</a>

</body>
</html>