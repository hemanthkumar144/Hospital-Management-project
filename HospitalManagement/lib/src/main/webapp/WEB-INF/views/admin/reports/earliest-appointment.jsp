<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Earliest Appointment</title>
</head>

<body>

<h1>Earliest Appointment</h1>

<hr>

<%
    Object appointment = request.getAttribute("appointment");
%>

<% if (appointment == null) { %>

    <p>No appointments found.</p>

<% } else { %>

    <table border="1"
           cellpadding="10"
           cellspacing="0">

        <tr>
            <th>Appointment ID</th>
            <th>Patient</th>
            <th>Doctor</th>
            <th>Date</th>
            <th>Time</th>
        </tr>

        <tr>
            <td>${appointment.appointmentId}</td>
            <td>${appointment.patient.patientName}</td>
            <td>${appointment.doctor.name}</td>
            <td>${appointment.appointmentDate}</td>
            <td>${appointment.appointmentTime}</td>
        </tr>

    </table>

<% } %>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>