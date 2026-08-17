<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doctors by Experience</title>
</head>

<body>

<h1>Doctors by Experience</h1>

<hr>

<c:choose>

    <c:when test="${empty doctors}">
        <p>No doctors found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Staff ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Experience (Years)</th>
                <th>Consultation Fee</th>
            </tr>

            <c:forEach var="doctor"
                       items="${doctors}">

                <tr>

                    <td>${doctor.staffId}</td>

                    <td>${doctor.name}</td>

                    <td>${doctor.specialization}</td>

                    <td>${doctor.experience}</td>

                    <td>${doctor.consultationFee}</td>

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