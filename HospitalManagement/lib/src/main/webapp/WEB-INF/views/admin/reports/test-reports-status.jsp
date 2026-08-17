<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Laboratory Test Report Status</title>
</head>

<body>

<h1>Laboratory Test Report Status</h1>

<hr>

<h2>
    All Test Reports Delivered:
    ${allCompleted}
</h2>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>

</html>