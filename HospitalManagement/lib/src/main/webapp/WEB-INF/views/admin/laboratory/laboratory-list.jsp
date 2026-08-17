<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Laboratory Management</title>
</head>

<body>

<h1>Laboratory Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/laboratory/add">
    Add Laboratory Test
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

    <c:when test="${empty tests}">
        <p>No laboratory tests found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Test ID</th>
                <th>Test Name</th>
                <th>Description</th>
                <th>Charge</th>
                <th>Type</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="test"
                       items="${tests}">

                <tr>

                    <td>${test.testId}</td>

                    <td>${test.testName}</td>

                    <td>${test.testDescription}</td>

                    <td>${test.testCharge}</td>

                    <td>${test.testType}</td>

                    <td>

                        <c:choose>

                            <c:when test="${test.active}">
                                Active
                            </c:when>

                            <c:otherwise>
                                Inactive
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/laboratory/view/${test.testId}">
                            View
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/laboratory/edit/${test.testId}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/admin/laboratory/deactivate/${test.testId}"
                           onclick="return confirm('Deactivate this laboratory test?');">
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