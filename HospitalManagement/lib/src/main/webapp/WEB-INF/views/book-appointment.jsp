<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Appointment</title>
</head>
<body>

<h1>Book Appointment</h1>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/patient/book-appointment"
      method="post">

    <label>Doctor:</label>

<select name="doctorId" required>

    <option value="">Select Doctor</option>

    <c:forEach var="doctor" items="${doctors}">

        <option value="${doctor.staffId}">
            ${doctor.name}
        </option>

    </c:forEach>

</select>

    <br><br>

    <label>Date:</label>

    <input type="date"
           name="appointmentDate"
           required>

    <br><br>

    <label>Time:</label>

    <input type="time"
           name="appointmentTime"
           required>

    <br><br>

    <button type="submit">
        Book Appointment
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/patient/appointments">
    My Appointments
</a>

<br>

<a href="${pageContext.request.contextPath}/dashboard">
    Dashboard
</a>

</body>
</html>