<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Generate Bill</title>
</head>

<body>

<h1>Generate Bill</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<p>
    The system will automatically calculate consultation,
    medicine, laboratory and bed charges.
</p>

<form method="post"
      action="${pageContext.request.contextPath}/admin/bills/add">

    <label>Patient:</label>

    <select name="patientId"
            required>

        <option value="">
            -- Select Patient --
        </option>

        <c:forEach var="patient"
                   items="${patients}">

            <c:if test="${patient.active}">

                <option value="${patient.patientId}">

                    ${patient.patientId}
                    -
                    ${patient.patientName}

                </option>

            </c:if>

        </c:forEach>

    </select>

    <br><br>

    <button type="submit">
        Generate Bill
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/bills">
    Back to Bill List
</a>

</body>
</html>