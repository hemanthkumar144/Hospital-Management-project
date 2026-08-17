<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Doctors</title>
</head>

<body>

<h1>Available Doctors</h1>

<hr>

<c:choose>

    <c:when test="${empty doctors}">
        <p>No active doctors found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Staff ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Experience</th>
                <th>Consultation Fee</th>
                <th>Status</th>
            </tr>

            <c:forEach var="doctor"
                       items="${doctors}">

                <tr>

                    <td>
                        ${doctor.staffId}
                    </td>

                    <td>
                        ${doctor.name}
                    </td>

                    <td>
                        ${doctor.specialization}
                    </td>

                    <td>
                        ${doctor.experience}
                    </td>

                    <td>
                        ${doctor.consultationFee}
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${doctor.active}">
                                ACTIVE
                            </c:when>

                            <c:otherwise>
                                INACTIVE
                            </c:otherwise>
                        </c:choose>
                    </td>

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