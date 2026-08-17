<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Edit Patient</title>
</head>

<body>

<h1>Edit Patient</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/patients/edit">

    <input type="hidden"
           name="patientId"
           value="${patient.patientId}">

    <input type="hidden"
           name="userId"
           value="${patient.userId}">


    <label>Patient Name:</label>

    <input type="text"
           name="patientName"
           value="${patient.patientName}"
           required>

    <br><br>


    <label>Age:</label>

    <input type="number"
           name="age"
           value="${patient.age}"
           min="1"
           max="120"
           required>

    <br><br>


    <label>Gender:</label>

    <select name="gender" required>

        <option value="Male"
            ${patient.gender == 'Male'
                ? 'selected'
                : ''}>
            Male
        </option>

        <option value="Female"
            ${patient.gender == 'Female'
                ? 'selected'
                : ''}>
            Female
        </option>

        <option value="Other"
            ${patient.gender == 'Other'
                ? 'selected'
                : ''}>
            Other
        </option>

    </select>

    <br><br>


    <label>Phone:</label>

    <input type="text"
           name="phone"
           value="${patient.phone}"
           maxlength="10"
           required>

    <br><br>


    <h3>Address</h3>

    <input type="hidden"
           name="address.addressId"
           value="${patient.address.addressId}">


    <label>Street:</label>

    <input type="text"
           name="address.street"
           value="${patient.address.street}"
           required>

    <br><br>


    <label>City:</label>

    <input type="text"
           name="address.city"
           value="${patient.address.city}"
           required>

    <br><br>


    <label>State:</label>

    <input type="text"
           name="address.state"
           value="${patient.address.state}"
           required>

    <br><br>


   


    <button type="submit">
        Update Patient
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/patients">
    Back to Patient List
</a>

</body>

</html>