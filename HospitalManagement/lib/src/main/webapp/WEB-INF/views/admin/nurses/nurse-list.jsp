<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nurse Management</title>
</head>

<body>

<h1>Nurse Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/nurses/add">
    Add Nurse
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

    <c:when test="${empty nurses}">
        <p>No nurses found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Nurse ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Gender</th>
                <th>Phone</th>
                <th>Salary</th>
                <th>Department</th>
                <th>Shift</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="nurse"
                       items="${nurses}">

                <tr>

                    <td>${nurse.staffId}</td>

                    <td>${nurse.name}</td>

                    <td>${nurse.age}</td>

                    <td>${nurse.gender}</td>

                    <td>${nurse.phone}</td>

                    <td>${nurse.salary}</td>

                    <td>
                        ${nurse.department.departmentName}
                    </td>

                    <td>${nurse.shift}</td>

                    <td>
                        <c:choose>
                            <c:when test="${nurse.active}">
                                Active
                            </c:when>
                            <c:otherwise>
                                Inactive
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/nurses/view/${nurse.staffId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/nurses/edit/${nurse.staffId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/nurses/deactivate/${nurse.staffId}"
                           onclick="return confirm('Are you sure you want to deactivate this nurse?');">
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