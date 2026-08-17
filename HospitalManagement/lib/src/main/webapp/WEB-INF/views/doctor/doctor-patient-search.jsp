<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Search Patient</title>

</head>

<body>

<h1>View Patient</h1>

<hr>

<form method="get"
      action="${pageContext.request.contextPath}/doctor/patient/search">

    <label>Patient ID:</label>

    <input type="text"
           name="patientId"
           required>

    <button type="submit">
        Search
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/doctor">
    Back to Dashboard
</a>

</body>

</html>