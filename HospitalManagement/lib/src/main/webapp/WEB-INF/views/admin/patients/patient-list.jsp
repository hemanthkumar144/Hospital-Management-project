<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Patient Management</title>
</head>

<body>

<h1>Patient Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/dashboard">
    Back to Admin Dashboard
</a>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

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
                <th>Address</th>
                <th>Status</th>
                <th>Actions</th>
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
                        ${patient.address.street},
                        ${patient.address.city}
                    </td>

                    <td>

                        <c:choose>

                            <c:when test="${patient.active}">
                                Active
                            </c:when>

                            <c:otherwise>
                                Inactive
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/patients/view/${patient.patientId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/patients/edit/${patient.patientId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/patients/delete/${patient.patientId}"
                           onclick="return confirm('Are you sure you want to delete this patient?');">
                            Deactivate
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

</body>

</html>