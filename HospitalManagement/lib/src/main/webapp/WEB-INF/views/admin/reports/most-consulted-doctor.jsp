<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Most Consulted Doctor</title>
</head>

<body>

<h1>Most Consulted Doctor</h1>

<hr>

<%
    Object result = request.getAttribute("result");
%>

<% if (result == null) { %>

    <p>No appointment data found.</p>

<% } else { %>

    <table border="1"
           cellpadding="10"
           cellspacing="0">

        <tr>
            <th>Doctor</th>
            <th>Total Appointments</th>
        </tr>

        <tr>
            <td>${result.key}</td>
            <td>${result.value}</td>
        </tr>

    </table>

<% } %>

<br>

<a href="${pageContext.request.contextPath}/admin/reports">
    Back to Reports
</a>

</body>
</html>