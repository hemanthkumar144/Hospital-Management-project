<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bed Details</title>
</head>

<body>

<h1>Bed Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Bed ID</th>
        <td>${bed.bedId}</td>
    </tr>

    <tr>
        <th>Ward ID</th>
        <td>${bed.ward.wardId}</td>
    </tr>

    <tr>
        <th>Ward Name</th>
        <td>${bed.ward.wardName}</td>
    </tr>

    <tr>
        <th>Ward Type</th>
        <td>${bed.ward.wardType}</td>
    </tr>

    <tr>
        <th>Availability</th>
        <td>${bed.availability}</td>
    </tr>

    <tr>
        <th>Patient ID</th>

        <td>

            <c:choose>

                <c:when test="${not empty bed.patient}">
                    ${bed.patient.patientId}
                </c:when>

                <c:otherwise>
                    -
                </c:otherwise>

            </c:choose>

        </td>

    </tr>

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${bed.active}">
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

<a href="${pageContext.request.contextPath}/admin/beds/edit/${bed.bedId}">
    Edit Bed
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/beds">
    Back to Bed List
</a>

</body>
</html>