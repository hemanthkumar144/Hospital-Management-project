<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assign Laboratory Test</title>
</head>

<body>

<h1>Assign Laboratory Test</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/patient-tests/add">

    <label>Patient:</label>

    <select name="patientId" required>

        <option value="">
            -- Select Patient --
        </option>

        <c:forEach var="patient"
                   items="${patients}">

            <option value="${patient.patientId}">

                ${patient.patientId}
                -
                ${patient.patientName}

            </option>

        </c:forEach>

    </select>

    <br><br>


    <label>Laboratory Test:</label>

    <select name="testId" required>

        <option value="">
            -- Select Laboratory Test --
        </option>

        <c:forEach var="test"
                   items="${tests}">

            <c:if test="${test.active}">

                <option value="${test.testId}">

                    ${test.testId}
                    -
                    ${test.testName}
                    -
                    ${test.testCharge}

                </option>

            </c:if>

        </c:forEach>

    </select>

    <br><br>

    <button type="submit">
        Assign Test
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/patient-tests">
    Back to Patient Test List
</a>

</body>
</html>