<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patients</title>
</head>

<body>

<h1>Patient Management</h1>

<table border="1" cellpadding="8">

    <thead>
        <tr>
            <th>Patient ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Gender</th>
            <th>Phone</th>
            <th>Active</th>
            <th>User ID</th>
        </tr>
    </thead>

    <tbody>

        <c:forEach var="patient" items="${patients}">

            <tr>

                <td>${patient.patientId}</td>

                <td>${patient.patientName}</td>

                <td>${patient.age}</td>

                <td>${patient.gender}</td>

                <td>${patient.phone}</td>

                <td>${patient.active}</td>

                <td>${patient.userId}</td>

            </tr>

        </c:forEach>

    </tbody>

</table>

</body>
</html>