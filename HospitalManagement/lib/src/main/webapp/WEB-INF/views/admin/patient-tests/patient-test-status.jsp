<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Test Status</title>
</head>

<body>

<h1>Update Patient Test Status</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Patient Test ID</th>
        <td>${patientTest.patientTestId}</td>
    </tr>

    <tr>
        <th>Patient ID</th>
        <td>${patientTest.patientId}</td>
    </tr>

    <tr>
        <th>Test ID</th>
        <td>${patientTest.testId}</td>
    </tr>

    <tr>
        <th>Current Status</th>
        <td>${patientTest.status}</td>
    </tr>

</table>

<br>

<form method="post"
      action="${pageContext.request.contextPath}/admin/patient-tests/status">

    <input type="hidden"
           name="patientTestId"
           value="${patientTest.patientTestId}">

    <label>New Status:</label>

    <select name="status" required>

        <option value="">
            -- Select Status --
        </option>

        <c:forEach var="status"
                   items="${statuses}">

            <option value="${status}">
                ${status}
            </option>

        </c:forEach>

    </select>

    <br><br>

    <button type="submit">
        Update Status
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/patient-tests">
    Back to Patient Test List
</a>

</body>
</html>