<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pending Appointments</title>
</head>

<body>

<h1>Pending Appointments</h1>

<hr>

<c:choose>

    <c:when test="${empty appointments}">
        <p>No pending appointments found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Appointment ID</th>
                <th>Patient</th>
                <th>Doctor</th>
                <th>Date</th>
                <th>Time</th>
                <th>Active</th>
            </tr>

            <c:forEach var="appointment"
                       items="${appointments}">

                <tr>

                    <td>
                        ${appointment.appointmentId}
                    </td>

                    <td>
                        ${appointment.patient.patientName}
                    </td>

                    <td>
                        ${appointment.doctor.name}
                    </td>

                    <td>
                        ${appointment.appointmentDate}
                    </td>

                    <td>
                        ${appointment.appointmentTime}
                    </td>

                    <td>
                        ${appointment.active}
                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>