<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Department Management</title>
</head>

<body>

<h1>Department Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/departments/add">
    Add Department
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

    <c:when test="${empty departments}">
        <p>No departments found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Department ID</th>
                <th>Department Name</th>
                <th>Location</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="department"
                       items="${departments}">

                <tr>

                    <td>
                        ${department.departmentId}
                    </td>

                    <td>
                        ${department.departmentName}
                    </td>

                    <td>
                        ${department.location}
                    </td>

                    <td>

                        <c:choose>

                            <c:when test="${department.active}">
                                Active
                            </c:when>

                            <c:otherwise>
                                Inactive
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/departments/view/${department.departmentId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/departments/edit/${department.departmentId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/departments/delete/${department.departmentId}"
                           onclick="return confirm('Are you sure you want to delete this department?');">
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