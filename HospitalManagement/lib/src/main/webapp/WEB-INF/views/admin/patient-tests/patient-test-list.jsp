<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Test Management</title>
</head>

<body>

<h1>Patient Test Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/patient-tests/add">
    Assign Laboratory Test
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

    <c:when test="${empty patientTests}">
        <p>No patient tests found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Patient Test ID</th>
                <th>Patient ID</th>
                <th>Test ID</th>
                <th>Test Date</th>
                <th>Charge</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="patientTest"
                       items="${patientTests}">

                <tr>

                    <td>
                        ${patientTest.patientTestId}
                    </td>

                    <td>
                        ${patientTest.patientId}
                    </td>

                    <td>
                        ${patientTest.testId}
                    </td>

                    <td>
                        ${patientTest.testDate}
                    </td>

                    <td>
                        ${patientTest.charge}
                    </td>

                    <td>
                        ${patientTest.status}
                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/patient-tests/view/${patientTest.patientTestId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/patient-tests/status/${patientTest.patientTestId}">
                            Update Status
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

</body>
</html>