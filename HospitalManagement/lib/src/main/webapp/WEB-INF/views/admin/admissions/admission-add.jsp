<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admit Patient</title>
</head>

<body>

<h1>Admit Patient</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="get"
      action="${pageContext.request.contextPath}/admin/admissions/available-beds">

    <label>Patient:</label>

    <select name="patientId" required>

        <option value="">
            -- Select Patient --
        </option>

        <c:forEach var="patient"
                   items="${patients}">

            <c:if test="${patient.active}">

                <option value="${patient.patientId}"
                    ${patient.patientId == patientId
                        ? 'selected'
                        : ''}>

                    ${patient.patientId}
                    -
                    ${patient.patientName}

                </option>

            </c:if>

        </c:forEach>

    </select>

    <br><br>


    <label>Ward Type:</label>

    <select name="wardType" required>

        <option value="">
            -- Select Ward Type --
        </option>

        <c:forEach var="type"
                   items="${wardTypes}">

            <option value="${type}"
                ${type == wardType
                    ? 'selected'
                    : ''}>

                ${type}

            </option>

        </c:forEach>

    </select>

    <br><br>

    <button type="submit">
        Find Available Bed
    </button>

</form>


<c:if test="${not empty availableBed}">

    <hr>

    <h2>Available Bed Found</h2>

    <table border="1"
           cellpadding="10"
           cellspacing="0">

        <tr>
            <th>Bed ID</th>
            <td>
                ${availableBed.bedId}
            </td>
        </tr>

        <tr>
            <th>Ward</th>
            <td>
                ${availableBed.ward.wardName}
            </td>
        </tr>

        <tr>
            <th>Ward Type</th>
            <td>
                ${availableBed.ward.wardType}
            </td>
        </tr>

        <tr>
            <th>Availability</th>
            <td>
                ${availableBed.availability}
            </td>
        </tr>

    </table>

    <br>

    <form method="post"
          action="${pageContext.request.contextPath}/admin/admissions/add">

        <input type="hidden"
               name="patientId"
               value="${patientId}">

        <input type="hidden"
               name="wardType"
               value="${wardType}">

        <input type="hidden"
               name="selectedBedId"
               value="${availableBed.bedId}">

        <button type="submit">
            Admit Patient
        </button>

    </form>

</c:if>


<br>

<a href="${pageContext.request.contextPath}/admin/admissions">
    Back to Admission List
</a>

</body>
</html>