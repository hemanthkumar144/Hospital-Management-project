<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Appointments by Status</title>
</head>

<body>

<h1>Appointments by Status</h1>

<hr>

<c:choose>

    <c:when test="${empty groupedAppointments}">
        <p>No appointment data found.</p>
    </c:when>

    <c:otherwise>

        <c:forEach var="entry"
                   items="${groupedAppointments}">

            <h2>${entry.key}</h2>

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

                <c:forEach var="appointment"
                           items="${entry.value}">

                    <tr>
                        <td>${appointment.appointmentId}</td>
                        <td>${appointment.patient.patientName}</td>
                        <td>${appointment.doctor.name}</td>
                        <td>${appointment.appointmentDate}</td>
                        <td>${appointment.appointmentTime}</td>
                    </tr>

                </c:forEach>

            </table>

            <br>

        </c:forEach>

    </c:otherwise>

</c:choose>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>