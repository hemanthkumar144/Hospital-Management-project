<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Test Details</title>
</head>

<body>

<h1>Patient Test Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Patient Test ID</th>
        <td>${patientTest.patientTestId}</td>
    </tr>

    <tr>
        <th>Patient ID</th>
        <td>${patientTest.patientId}</td>
    </tr>

    <tr>
        <th>Laboratory Test ID</th>
        <td>${patientTest.testId}</td>
    </tr>

    <tr>
        <th>Test Date</th>
        <td>${patientTest.testDate}</td>
    </tr>

    <tr>
        <th>Charge</th>
        <td>${patientTest.charge}</td>
    </tr>

    <tr>
        <th>Status</th>
        <td>${patientTest.status}</td>
    </tr>

</table>

<br>

<a href="${pageContext.request.contextPath}/admin/patient-tests/status/${patientTest.patientTestId}">
    Update Status
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/patient-tests">
    Back to Patient Test List
</a>

</body>
</html>