<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pending Bills</title>
</head>

<body>

<h1>Pending Bills</h1>

<hr>

<form method="get"
      action="${pageContext.request.contextPath}/admin/bills/pending">

    <label>Patient ID:</label>

    <input type="text"
           name="patientId"
           value="${patientId}"
           required>

    <button type="submit">
        Search
    </button>

</form>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:choose>

    <c:when test="${empty bills}">
        <p>No pending bills found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Bill ID</th>
                <th>Patient ID</th>
                <th>Bill Date</th>
                <th>Total Amount</th>
                <th>Status</th>
                <th>Action</th>
            </tr>

            <c:forEach var="bill"
                       items="${bills}">

                <tr>

                    <td>
                        ${bill.billId}
                    </td>

                    <td>
                        ${bill.patient.patientId}
                    </td>

                    <td>
                        ${bill.billDate}
                    </td>

                    <td>
                        ${bill.totalAmount}
                    </td>

                    <td>
                        ${bill.status}
                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/bills/view/${bill.billId}">
                            View
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/admin/bills">
    Back to Bill Management
</a>

</body>
</html>