<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Patients</title>

</head>

<body>

<h1>Patients</h1>

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

                <th>Patient ID</th>

                <th>Name</th>

                <th>Age</th>

                <th>Gender</th>

                <th>Phone</th>

                <th>Action</th>

            </tr>


            <c:forEach var="patient"
                       items="${patients}">

                <tr>

                    <td>
                        ${patient.patientId}
                    </td>

                    <td>
                        ${patient.patientName}
                    </td>

                    <td>
                        ${patient.age}
                    </td>

                    <td>
                        ${patient.gender}
                    </td>

                    <td>
                        ${patient.phone}
                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/doctor/patient/${patient.patientId}">
                            View
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/doctor">
    Back to Dashboard
</a>

</body>

</html>