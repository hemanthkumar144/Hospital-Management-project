<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Medicine Stock</title>
</head>

<body>

<h1>Medicine Stock</h1>

<hr>

<c:choose>

    <c:when test="${empty medicines}">
        <p>No medicine stock data found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Medicine ID</th>
                <th>Medicine Name</th>
                <th>Manufacturer</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>

            <c:forEach var="medicine"
                       items="${medicines}">

                <tr>
                    <td>${medicine.medicineId}</td>
                    <td>${medicine.medicineName}</td>
                    <td>${medicine.manufacturer}</td>
                    <td>${medicine.price}</td>
                    <td>${medicine.quantity}</td>
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