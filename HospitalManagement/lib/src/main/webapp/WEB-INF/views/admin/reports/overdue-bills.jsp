<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Overdue Bills</title>
</head>

<body>

<h1>Overdue Bills</h1>

<hr>

<c:choose>

    <c:when test="${empty bills}">
        <p>No overdue bills found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Bill ID</th>
                <th>Patient</th>
                <th>Total Amount</th>
                <th>Bill Date</th>
                <th>Status</th>
            </tr>

            <c:forEach var="bill"
                       items="${bills}">

                <tr>
                    <td>${bill.billId}</td>
                    <td>${bill.patient.patientName}</td>
                    <td>${bill.totalAmount}</td>
                    <td>${bill.billDate}</td>
                    <td>${bill.status}</td>
                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>