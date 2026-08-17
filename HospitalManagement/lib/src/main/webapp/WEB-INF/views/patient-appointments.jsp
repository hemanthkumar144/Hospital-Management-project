<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>My Appointments</title>
</head>

<body>

<h1>My Appointments</h1>

<c:choose>

    <c:when test="${empty appointments}">
        <p>No appointments found.</p>
    </c:when>

    <c:otherwise>

        <table border="1">

            <tr>
                <th>Appointment ID</th>
                <th>Doctor</th>
                <th>Date</th>
                <th>Time</th>
                <th>Status</th>
            </tr>

            <c:forEach var="appointment" items="${appointments}">

                <tr>

                    <td>
                        ${appointment.appointmentId}
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
                        ${appointment.active ? 'Active' : 'Inactive'}
                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/dashboard">
    Dashboard
</a>

</body>
</html>