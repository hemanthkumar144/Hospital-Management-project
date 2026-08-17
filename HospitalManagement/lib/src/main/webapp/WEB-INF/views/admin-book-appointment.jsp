<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Book Appointment</title>
</head>

<body>

<h1>Book Appointment</h1>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/patient/book-appointment">

    <label>Patient:</label>

    <select name="patientId" required>

        <option value="">-- Select Patient --</option>

        <c:forEach var="patient" items="${patients}">

            <option value="${patient.patientId}">
                ${patient.patientName}
            </option>

        </c:forEach>

    </select>

    <br><br>

    <label>Doctor:</label>

    <select name="doctorId" required>

        <option value="">-- Select Doctor --</option>

        <c:forEach var="doctor" items="${doctors}">

            <option value="${doctor.staffId}">
                ${doctor.name}
            </option>

        </c:forEach>

    </select>

    <br><br>

    <label>Appointment Date:</label>

    <input type="date"
           name="appointmentDate"
           required>

    <br><br>

    <label>Appointment Time:</label>

    <input type="time"
           name="appointmentTime"
           required>

    <br><br>

    <button type="submit">
        Book Appointment
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/dashboard">
    Back to Admin Dashboard
</a>

<br><br>

<a href="${pageContext.request.contextPath}/logout">
    Logout
</a>

</body>

</html>