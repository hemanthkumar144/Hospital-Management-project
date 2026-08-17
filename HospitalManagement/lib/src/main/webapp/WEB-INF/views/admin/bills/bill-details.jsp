<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bill Details</title>
</head>

<body>

<h1>Bill Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Bill ID</th>
        <td>${bill.billId}</td>
    </tr>

    <tr>
        <th>Patient ID</th>
        <td>${bill.patient.patientId}</td>
    </tr>

    <tr>
        <th>Patient Name</th>
        <td>${bill.patient.patientName}</td>
    </tr>

    <tr>
        <th>Bill Date</th>
        <td>${bill.billDate}</td>
    </tr>

    <tr>
        <th>Consultation Fee</th>
        <td>${bill.consultationFee}</td>
    </tr>

    <tr>
        <th>Medicine Charges</th>
        <td>${bill.medicineCharges}</td>
    </tr>

    <tr>
        <th>Laboratory Charges</th>
        <td>${bill.laboratoryCharges}</td>
    </tr>

    <tr>
        <th>Bed Charges</th>
        <td>${bill.bedCharges}</td>
    </tr>

    <tr>
        <th>Total Amount</th>
        <td><strong>${bill.totalAmount}</strong></td>
    </tr>

    <tr>
        <th>Status</th>
        <td>${bill.status}</td>
    </tr>

</table>

<br>

<c:if test="${bill.status == 'PENDING'}">

    <a href="${pageContext.request.contextPath}/admin/bills/pay/${bill.billId}"
       onclick="return confirm('Mark this bill as paid?');">
        Mark as Paid
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/admin/bills/cancel/${bill.billId}"
       onclick="return confirm('Cancel this bill?');">
        Cancel Bill
    </a>

    <br><br>

</c:if>

<a href="${pageContext.request.contextPath}/admin/bills">
    Back to Bill List
</a>

</body>
</html>