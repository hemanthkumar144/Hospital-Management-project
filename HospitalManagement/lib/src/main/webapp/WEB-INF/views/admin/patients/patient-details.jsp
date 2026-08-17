<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>
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

    <tr>
        <th>User ID</th>
        <td>${patient.userId}</td>
    </tr>

    <tr>
        <th>Address ID</th>
        <td>${patient.address.addressId}</td>
    </tr>

    <tr>
        <th>Street</th>
        <td>${patient.address.street}</td>
    </tr>

    <tr>
        <th>City</th>
        <td>${patient.address.city}</td>
    </tr>

    <tr>
        <th>State</th>
        <td>${patient.address.state}</td>
    </tr>

  

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${patient.active}">
                    Active
                </c:when>

                <c:otherwise>
                    Inactive
                </c:otherwise>

            </c:choose>

        </td>

    </tr>

</table>

<br>

<a href="${pageContext.request.contextPath}/admin/patients/edit/${patient.patientId}">
    Edit Patient
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/patients">
    Back to Patient List
</a>

</body>

</html>