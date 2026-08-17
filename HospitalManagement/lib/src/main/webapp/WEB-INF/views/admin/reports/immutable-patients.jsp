<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Immutable Patient List</title>
</head>

<body>

<h1>Immutable Patient List</h1>

<hr>

<c:choose>

    <c:when test="${empty patients}">
        <p>No patients found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>S.No</th>
                <th>Patient ID</th>
                <th>Patient Name</th>
                <th>Age</th>
                <th>Gender</th>
            </tr>

            <c:forEach var="patient"
                       items="${patients}"
                       varStatus="status">

                <tr>
                    <td>${status.count}</td>
                    <td>${patient.patientId}</td>
                    <td>${patient.patientName}</td>
                    <td>${patient.age}</td>
                    <td>${patient.gender}</td>
                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>

</html>