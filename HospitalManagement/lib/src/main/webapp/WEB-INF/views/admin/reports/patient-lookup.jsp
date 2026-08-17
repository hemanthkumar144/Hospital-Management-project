<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Patient Lookup</title>
</head>

<body>

<h1>Patient Lookup</h1>

<hr>

<form method="get"
      action="${pageContext.request.contextPath}/admin/reports/patient-lookup">

    <label>Patient ID:</label>

    <input type="text"
           name="patientId"
           value="${patientId}"
           required>

    <button type="submit">
        Search
    </button>

</form>

<br>

<c:if test="${not empty patient}">

    <h2>Patient Details</h2>

    <table border="1"
           cellpadding="10"
           cellspacing="0">

        <tr>
            <th>Patient ID</th>
            <td>${patient.patientId}</td>
        </tr>

        <tr>
            <th>Patient Name</th>
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

</c:if>

<c:if test="${not empty patientId and empty patient}">
    <p>Patient not found.</p>
</c:if>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>

</html>