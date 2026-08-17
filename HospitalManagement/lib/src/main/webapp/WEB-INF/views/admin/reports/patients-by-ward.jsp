<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patients by Ward</title>
</head>

<body>

<h1>Patients by Ward</h1>

<hr>

<form method="get"
      action="${pageContext.request.contextPath}/admin/reports/patients-by-ward">

    <label>Ward Name:</label>

    <input type="text"
           name="wardName"
           value="${wardName}"
           required>

    <button type="submit">
        Search
    </button>

</form>

<hr>

<c:if test="${not empty wardName}">

    <h2>
        Ward: ${wardName}
    </h2>

    <c:choose>

        <c:when test="${empty patients}">
            <p>No patients found in this ward.</p>
        </c:when>

        <c:otherwise>

            <table border="1"
                   cellpadding="10"
                   cellspacing="0">

                <tr>
                    <th>Patient ID</th>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Gender</th>
                    <th>Phone</th>
                </tr>

                <c:forEach var="patient"
                           items="${patients}">

                    <tr>
                        <td>${patient.patientId}</td>
                        <td>${patient.patientName}</td>
                        <td>${patient.age}</td>
                        <td>${patient.gender}</td>
                        <td>${patient.phone}</td>
                    </tr>

                </c:forEach>

            </table>

        </c:otherwise>

    </c:choose>

</c:if>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>