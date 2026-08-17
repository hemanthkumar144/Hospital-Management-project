<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bed Management</title>
</head>

<body>

<h1>Bed Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/beds/add">
    Add Bed
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

    <c:when test="${empty beds}">
        <p>No beds found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Bed ID</th>
                <th>Ward</th>
                <th>Availability</th>
                <th>Patient</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="bed"
                       items="${beds}">

                <tr>

                    <td>
                        ${bed.bedId}
                    </td>

                    <td>
                        ${bed.ward.wardName}
                        (${bed.ward.wardId})
                    </td>

                    <td>
                        ${bed.availability}
                    </td>

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

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/beds/view/${bed.bedId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/beds/edit/${bed.bedId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/beds/delete/${bed.bedId}"
                           onclick="return confirm('Are you sure you want to deactivate this bed?');">
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