<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Average Bill Amount</title>
</head>

<body>

<h1>Average Bill Amount</h1>

<hr>

<h2>
    Average Bill:
    ${average}
</h2>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>

</html>