<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doctors by Specialization</title>
</head>

<body>

<h1>Doctors by Specialization</h1>

<hr>

<c:choose>

    <c:when test="${empty groupedDoctors}">
        <p>No doctors found.</p>
    </c:when>

    <c:otherwise>

        <c:forEach var="entry"
                   items="${groupedDoctors}">

            <h2>${entry.key}</h2>

            <table border="1"
                   cellpadding="10"
                   cellspacing="0">

                <tr>
                    <th>Staff ID</th>
                    <th>Name</th>
                    <th>Experience</th>
                    <th>Consultation Fee</th>
                </tr>

                <c:forEach var="doctor"
                           items="${entry.value}">

                    <tr>
                        <td>${doctor.staffId}</td>
                        <td>${doctor.name}</td>
                        <td>${doctor.experience}</td>
                        <td>${doctor.consultationFee}</td>
                    </tr>

                </c:forEach>

            </table>

            <br>

        </c:forEach>

    </c:otherwise>

</c:choose>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>