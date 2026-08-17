<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Bill Summary Statistics</title>
</head>

<body>

<h1>Bill Summary Statistics</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Statistic</th>
        <th>Value</th>
    </tr>

    <tr>
        <td>Total Bills</td>
        <td>${statistics.count}</td>
    </tr>

    <tr>
        <td>Total Amount</td>
        <td>${statistics.total}</td>
    </tr>

    <tr>
        <td>Average Bill</td>
        <td>${statistics.average}</td>
    </tr>

    <tr>
        <td>Highest Bill</td>
        <td>${statistics.highest}</td>
    </tr>

    <tr>
        <td>Lowest Bill</td>
        <td>${statistics.lowest}</td>
    </tr>

</table>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>