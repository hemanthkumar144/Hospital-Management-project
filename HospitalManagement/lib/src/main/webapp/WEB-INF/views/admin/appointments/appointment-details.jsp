<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Appointment Details</title>
</head>

<body>

<h1>Appointment Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Appointment ID</th>
        <td>${appointment.appointmentId}</td>
    </tr>

    <tr>
        <th>Patient ID</th>
        <td>${appointment.patient.patientId}</td>
    </tr>

    <tr>
        <th>Patient Name</th>
        <td>${appointment.patient.patientName}</td>
    </tr>

    <tr>
        <th>Doctor ID</th>
        <td>${appointment.doctor.staffId}</td>
    </tr>

    <tr>
        <th>Doctor Name</th>
        <td>${appointment.doctor.name}</td>
    </tr>

    <tr>
        <th>Date</th>
        <td>${appointment.appointmentDate}</td>
    </tr>

    <tr>
        <th>Time</th>
        <td>${appointment.appointmentTime}</td>
    </tr>

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${appointment.active}">
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

<a href="${pageContext.request.contextPath}/admin/appointments/edit/${appointment.appointmentId}">
    Edit Appointment
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/appointments">
    Back to Appointment List
</a>

</body>
</html>