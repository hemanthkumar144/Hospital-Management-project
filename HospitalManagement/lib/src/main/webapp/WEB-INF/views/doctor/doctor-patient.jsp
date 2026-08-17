<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Patient Details</title>

</head>

<body>

<h1>Patient Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Patient ID</th>
        <td>${patient.patientId}</td>
    </tr>

    <tr>
        <th>Name</th>
        <td>${patient.patientName}</td>
    </tr>

    <tr>
        <th>Age</th>
        <td>${patient.age}</td>
    </tr>

    <tr>
        <th>Gender</th>
        <td>${patient.gender}</td>
    </tr>

    <tr>
        <th>Phone</th>
        <td>${patient.phone}</td>
    </tr>

</table>

<br>

<a href="${pageContext.request.contextPath}/doctor/patients">
    Back to Patients
</a>

</body>

</html>