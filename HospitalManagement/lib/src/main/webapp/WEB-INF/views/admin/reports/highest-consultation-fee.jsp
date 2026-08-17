<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Highest Consultation Fee</title>
</head>

<body>

<h1>Doctor with Highest Consultation Fee</h1>

<hr>

<%
    Object doctor = request.getAttribute("doctor");
%>

<% if (doctor == null) { %>

    <p>No doctor found.</p>

<% } else { %>

    <table border="1"
           cellpadding="10"
           cellspacing="0">

        <tr>
            <th>Staff ID</th>
            <th>Name</th>
            <th>Specialization</th>
            <th>Experience</th>
            <th>Consultation Fee</th>
        </tr>

        <tr>
            <td>${doctor.staffId}</td>
            <td>${doctor.name}</td>
            <td>${doctor.specialization}</td>
            <td>${doctor.experience}</td>
            <td>${doctor.consultationFee}</td>
        </tr>

    </table>

<% } %>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>