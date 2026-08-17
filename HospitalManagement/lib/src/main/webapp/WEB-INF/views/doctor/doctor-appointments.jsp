<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Doctor Appointments</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
        }

        .navbar {
            background: #1976d2;
            color: white;
            padding: 20px 40px;
            display: flex;
            justify-content: space-between;
        }

        .container {
            width: 90%;
            margin: 35px auto;
        }

        .box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th,
        td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background: #1976d2;
            color: white;
        }

        .btn {
            text-decoration: none;
            padding: 7px 12px;
            background: #1976d2;
            color: white;
            border-radius: 5px;
            border: none;
            cursor: pointer;
        }

        .cancel {
            background: #d32f2f;
        }

        .back {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #1976d2;
        }

    </style>

</head>

<body>

<div class="navbar">

    <h2>Doctor Appointments</h2>

    <a href="${pageContext.request.contextPath}/doctor/logout"
       style="color:white;">
        Logout
    </a>

</div>

<div class="container">

    <div class="box">

        <h1>My Appointments</h1>

        <!-- DATE FILTER -->

        <form method="get"
              action="${pageContext.request.contextPath}/doctor/appointments">

            <label>From Date:</label>

            <input type="date"
                   name="fromDate"
                   value="${fromDate}">

            <label>To Date:</label>

            <input type="date"
                   name="toDate"
                   value="${toDate}">

            <button type="submit"
                    class="btn">
                Search
            </button>

        </form>


        <c:choose>

            <c:when test="${empty appointments}">

                <p>
                    No appointments found.
                </p>

            </c:when>

            <c:otherwise>

                <table>

                    <tr>

                        <th>Appointment ID</th>

                        <th>Date</th>

                        <th>Time</th>

                        <th>Patient ID</th>

                        <th>Patient Name</th>

                        <th>Action</th>

                    </tr>


                    <c:forEach var="appointment"
                               items="${appointments}">

                        <tr>

                            <td>
                                ${appointment.appointmentId}
                            </td>

                            <td>
                                ${appointment.appointmentDate}
                            </td>

                            <td>
                                ${appointment.appointmentTime}
                            </td>

                            <td>
                                ${appointment.patient.patientId}
                            </td>

                            <td>
                                ${appointment.patient.patientName}
                            </td>

                            <td>

                                <a class="btn"
                                   href="${pageContext.request.contextPath}/doctor/appointment/${appointment.appointmentId}">
                                    View
                                </a>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/doctor/appointment/${appointment.appointmentId}/cancel"
                                      style="display:inline;">

                                    <button type="submit"
                                            class="btn cancel"
                                            onclick="return confirm('Cancel this appointment?');">
                                        Cancel
                                    </button>

                                </form>

                            </td>

                        </tr>

                    </c:forEach>

                </table>

            </c:otherwise>

        </c:choose>


        <a class="back"
           href="${pageContext.request.contextPath}/doctor">
            ← Back to Dashboard
        </a>

    </div>

</div>

</body>

</html>