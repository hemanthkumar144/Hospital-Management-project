<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ward Management</title>
</head>

<body>

<h1>Ward Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/wards/add">
    Add Ward
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

    <c:when test="${empty wards}">
        <p>No wards found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Ward ID</th>
                <th>Ward Name</th>
                <th>Ward Type</th>
                <th>Bed Charge</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="ward"
                       items="${wards}">

                <tr>

                    <td>
                        ${ward.wardId}
                    </td>

                    <td>
                        ${ward.wardName}
                    </td>

                    <td>
                        ${ward.wardType}
                    </td>

                    <td>
                        ${ward.bedCharge}
                    </td>

                    <td>

                        <c:choose>

                            <c:when test="${ward.active}">
                                Active
                            </c:when>

                            <c:otherwise>
                                Inactive
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/wards/view/${ward.wardId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/wards/edit/${ward.wardId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/wards/delete/${ward.wardId}"
                           onclick="return confirm('Are you sure you want to delete this ward?');">
Deactivate                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

</body>
</html>