<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Doctor Details</title>
</head>

<body>

<h1>Doctor Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Doctor ID</th>
        <td>${doctor.staffId}</td>
    </tr>

    <tr>
        <th>Name</th>
        <td>${doctor.name}</td>
    </tr>

    <tr>
        <th>Age</th>
        <td>${doctor.age}</td>
    </tr>

    <tr>
        <th>Gender</th>
        <td>${doctor.gender}</td>
    </tr>

    <tr>
        <th>Phone</th>
        <td>${doctor.phone}</td>
    </tr>

    <tr>
        <th>Salary</th>
        <td>${doctor.salary}</td>
    </tr>

    <tr>
        <th>Experience</th>
        <td>${doctor.experience}</td>
    </tr>

    <tr>
        <th>Specialization</th>
        <td>${doctor.specialization}</td>
    </tr>

    <tr>
        <th>Consultation Fee</th>
        <td>${doctor.consultationFee}</td>
    </tr>

    <tr>
        <th>Department</th>
        <td>${doctor.department.departmentName}</td>
    </tr>

</table>

<br>

<a href="${pageContext.request.contextPath}/admin/doctors/edit/${doctor.staffId}">
    Edit Doctor
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/doctors">
    Back to Doctor List
</a>

</body>

</html>