<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
</head>
<body>

<h1>My Profile</h1>

<p>Patient ID: ${patient.patientId}</p>
<p>Name: ${patient.patientName}</p>
<p>Age: ${patient.age}</p>
<p>Gender: ${patient.gender}</p>
<p>Phone: ${patient.phone}</p>

<a href="${pageContext.request.contextPath}/dashboard">
    Back to Dashboard
</a>

</body>
</html>