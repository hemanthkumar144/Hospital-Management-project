<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admission Management</title>
</head>

<body>

<h1>Admission Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/admissions/add">
    Admit Patient
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/dashboard">
    Back to Admin Dashboard
</a>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:choose>

    <c:when test="${empty admissions}">
        <p>No admissions found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Admission ID</th>
                <th>Patient ID</th>
                <th>Bed ID</th>
                <th>Ward</th>
                <th>Admission Date</th>
                <th>Discharge Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="admission"
                       items="${admissions}">

                <tr>

                    <td>
                        ${admission.admissionId}
                    </td>

                    <td>
                        ${admission.patient.patientId}
                    </td>

                    <td>
                        ${admission.bed.bedId}
                    </td>

                    <td>
                        ${admission.bed.ward.wardName}
                    </td>

                    <td>
                        ${admission.admissionDate}
                    </td>

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

                    <td>
                        ${admission.status}
                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/admissions/view/${admission.admissionId}">
                            View
                        </a>

                        <c:if test="${admission.status == 'ADMITTED'}">

                            |

                            <a href="${pageContext.request.contextPath}/admin/admissions/discharge/${admission.admissionId}"
                               onclick="return confirm('Discharge this patient?');">
                                Discharge
                            </a>

                        </c:if>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

</body>
</html>