<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Appointment Management</title>
</head>

<body>

<h1>Appointment Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/dashboard">
    Back to Admin Dashboard
</a>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:choose>

    <c:when test="${empty appointments}">
        <p>No appointments found.</p>
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
                <th>Status</th>
                <th>Actions</th>
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

                        <c:choose>

                            <c:when test="${appointment.active}">
                                Active
                            </c:when>

                            <c:otherwise>
                                Inactive
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/appointments/view/${appointment.appointmentId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/appointments/edit/${appointment.appointmentId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/appointments/delete/${appointment.appointmentId}"
                           onclick="return confirm('Are you sure you want to delete this appointment?');">
                            Deactivate
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

</body>
</html>