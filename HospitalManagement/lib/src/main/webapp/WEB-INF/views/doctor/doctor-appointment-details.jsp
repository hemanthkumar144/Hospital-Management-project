<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
        <th>Date</th>
        <td>${appointment.appointmentDate}</td>
    </tr>

    <tr>
        <th>Time</th>
        <td>${appointment.appointmentTime}</td>
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
        <th>Age</th>
        <td>${appointment.patient.age}</td>
    </tr>

    <tr>
        <th>Gender</th>
        <td>${appointment.patient.gender}</td>
    </tr>

    <tr>
        <th>Phone</th>
        <td>${appointment.patient.phone}</td>
    </tr>

</table>

<br>

<form method="post"
      action="${pageContext.request.contextPath}/doctor/appointment/${appointment.appointmentId}/cancel">

    <button type="submit"
            onclick="return confirm('Cancel this appointment?');">
        Cancel Appointment
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/doctor/appointments">
    Back to Appointments
</a>

</body>

</html>