<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Doctor Management</title>
</head>

<body>

<h1>Doctor Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/doctors/add">
    Add Doctor
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

    <c:when test="${empty doctors}">

        <p>No doctors found.</p>

    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>

                <th>Doctor ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Gender</th>
                <th>Phone</th>
                <th>Specialization</th>
                <th>Department</th>
                <th>Actions</th>

            </tr>

            <c:forEach var="doctor"
                       items="${doctors}">

                <tr>

                    <td>
                        ${doctor.staffId}
                    </td>

                    <td>
                        ${doctor.name}
                    </td>

                    <td>
                        ${doctor.age}
                    </td>

                    <td>
                        ${doctor.gender}
                    </td>

                    <td>
                        ${doctor.phone}
                    </td>

                    <td>
                        ${doctor.specialization}
                    </td>

                    <td>
                        ${doctor.department.departmentName}
                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/doctors/view/${doctor.staffId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/doctors/edit/${doctor.staffId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/doctors/delete/${doctor.staffId}"
                           onclick="return confirm('Are you sure you want to delete this doctor?');">
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