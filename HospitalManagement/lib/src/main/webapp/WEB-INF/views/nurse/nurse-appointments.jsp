<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Nurse - Appointments</title>

    <style>

        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            margin: 0;
            background: #f4f7fb;
        }

        .navbar {
            background: #00897b;
            color: white;
            padding: 20px 40px;

            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar h2 {
            margin: 0;
        }

        .logout {
            color: white;
            text-decoration: none;
            background: #d32f2f;
            padding: 9px 18px;
            border-radius: 5px;
        }

        .container {
            width: 92%;
            margin: 35px auto;
        }

        .box {
            background: white;
            padding: 25px;
            border-radius: 10px;

            box-shadow:
                    0 2px 10px rgba(0, 0, 0, 0.08);
        }

        h1 {
            color: #00897b;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        th,
        td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background: #00897b;
            color: white;
        }

        .btn {
            display: inline-block;
            text-decoration: none;

            background: #00897b;
            color: white;

            padding: 8px 14px;
            border-radius: 5px;
        }

        .btn:hover {
            background: #00695c;
        }

        .back {
            display: inline-block;
            margin-top: 25px;

            color: #00897b;
            text-decoration: none;
        }

        .empty {
            margin-top: 20px;
            color: #777;
        }

    </style>

</head>


<body>


<!-- =====================================================
     NAVBAR
     ===================================================== -->

<div class="navbar">

    <h2>Nurse - Appointments</h2>

    <a class="logout"
       href="${pageContext.request.contextPath}/nurse/logout">
        Logout
    </a>

</div>


<!-- =====================================================
     CONTENT
     ===================================================== -->

<div class="container">

    <div class="box">

        <h1>All Appointments</h1>


        <c:choose>

            <c:when test="${empty appointments}">

                <p class="empty">
                    No appointments found.
                </p>

            </c:when>


            <c:otherwise>

                <table>

                    <thead>

                    <tr>

                        <th>Appointment ID</th>

                        <th>Date</th>

                        <th>Time</th>

                        <th>Patient</th>

                        <th>Doctor</th>

                    </tr>

                    </thead>


                    <tbody>

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
                                ${appointment.patient.patientName}
                            </td>

                            <td>
    							${appointment.doctor.staffId}
							</td>

                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </c:otherwise>

        </c:choose>


        <a class="back"
           href="${pageContext.request.contextPath}/nurse">
            ← Back to Nurse Dashboard
        </a>

    </div>

</div>


</body>

</html>