<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Appointment</title>
</head>

<body>

<h1>Edit Appointment</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/appointments/edit">

    <input type="hidden"
           name="appointmentId"
           value="${appointment.appointmentId}">


    <label>Patient:</label>

    <select name="patientId" required>

        <c:forEach var="patient"
                   items="${patients}">

            <option value="${patient.patientId}"
                ${appointment.patient.patientId
                    == patient.patientId
                    ? 'selected'
                    : ''}>

                ${patient.patientName}
                (${patient.patientId})

            </option>

        </c:forEach>

    </select>

    <br><br>


    <label>Doctor:</label>

    <select name="doctorId" required>

        <c:forEach var="doctor"
                   items="${doctors}">

            <option value="${doctor.staffId}"
                ${appointment.doctor.staffId
                    == doctor.staffId
                    ? 'selected'
                    : ''}>

                ${doctor.name}
                (${doctor.staffId})

            </option>

        </c:forEach>

    </select>

    <br><br>


    <label>Appointment Date:</label>

    <input type="date"
           name="appointmentDate"
           value="${appointment.appointmentDate}"
           required>

    <br><br>


    <label>Appointment Time:</label>

    <input type="time"
           name="appointmentTime"
           value="${appointment.appointmentTime}"
           required>

    <br><br>


    <button type="submit">
        Update Appointment
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/appointments">
    Back to Appointment List
</a>

</body>
</html>