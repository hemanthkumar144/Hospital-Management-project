<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prescription Management</title>
</head>

<body>

<h1>Prescription Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/dashboard">
    Back to Admin Dashboard
</a>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:choose>

    <c:when test="${empty prescriptions}">
        <p>No prescriptions found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Prescription ID</th>
                <th>Patient ID</th>
                <th>Doctor ID</th>
                <th>Date</th>
                <th>Instructions</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="prescription"
                       items="${prescriptions}">

                <tr>

                    <td>
                        ${prescription.prescriptionId}
                    </td>

                    <td>
                        ${prescription.patient.patientId}
                    </td>

                    <td>
                        ${prescription.doctor.staffId}
                    </td>

                    <td>
                        ${prescription.prescriptionDate}
                    </td>

                    <td>
                        ${prescription.instructions}
                    </td>

                    <td>

                        <c:choose>

                            <c:when test="${prescription.active}">
                                Active
                            </c:when>

                            <c:otherwise>
                                Inactive
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/prescriptions/view/${prescription.prescriptionId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/prescriptions/edit/${prescription.prescriptionId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/prescriptions/delete/${prescription.prescriptionId}"
                           onclick="return confirm('Delete this prescription?');">
                            Delete
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

</body>
</html>