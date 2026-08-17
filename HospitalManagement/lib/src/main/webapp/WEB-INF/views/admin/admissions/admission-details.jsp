<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admission Details</title>
</head>

<body>

<h1>Admission Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Admission ID</th>
        <td>
            ${admission.admissionId}
        </td>
    </tr>

    <tr>
        <th>Patient ID</th>
        <td>
            ${admission.patient.patientId}
        </td>
    </tr>

    <tr>
        <th>Patient Name</th>
        <td>
            ${admission.patient.patientName}
        </td>
    </tr>

    <tr>
        <th>Bed ID</th>
        <td>
            ${admission.bed.bedId}
        </td>
    </tr>

    <tr>
        <th>Ward ID</th>
        <td>
            ${admission.bed.ward.wardId}
        </td>
    </tr>

    <tr>
        <th>Ward Name</th>
        <td>
            ${admission.bed.ward.wardName}
        </td>
    </tr>

    <tr>
        <th>Ward Type</th>
        <td>
            ${admission.bed.ward.wardType}
        </td>
    </tr>

    <tr>
        <th>Admission Date</th>
        <td>
            ${admission.admissionDate}
        </td>
    </tr>

    <tr>
        <th>Discharge Date</th>
        <td>

            <c:choose>

                <c:when test="${not empty admission.dischargeDate}">
                    ${admission.dischargeDate}
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
            ${admission.status}
        </td>
    </tr>

    <tr>
        <th>Active</th>
        <td>

            <c:choose>

                <c:when test="${admission.active}">
                    Yes
                </c:when>

                <c:otherwise>
                    No
                </c:otherwise>

            </c:choose>

        </td>
    </tr>

</table>

<br>

<c:if test="${admission.status == 'ADMITTED'}">

    <a href="${pageContext.request.contextPath}/admin/admissions/discharge/${admission.admissionId}"
       onclick="return confirm('Discharge this patient?');">
        Discharge Patient
    </a>

    <br><br>

</c:if>

<a href="${pageContext.request.contextPath}/admin/admissions">
    Back to Admission List
</a>

</body>
</html>