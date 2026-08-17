<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>ICU Bed Availability</title>
</head>

<body>

<h1>ICU Bed Availability</h1>

<hr>

<h2>
    ICU Bed Available:
    ${available}
</h2>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>

</html>