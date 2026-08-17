<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Write Prescription</title>

</head>

<body>

<h1>Write Prescription</h1>

<hr>

<form method="post"
      action="${pageContext.request.contextPath}/doctor/prescription/add">

    <!-- PATIENT -->

    <label>Patient ID:</label>

    <input type="text"
           name="patient.patientId"
           required>

    <br>
    <br>


    <!-- MEDICINE -->

    <label>Medicine:</label>

    <select name="medicine.medicineId"
            required>

        <option value="">
            -- Select Medicine --
        </option>

        <c:forEach var="medicine"
                   items="${medicines}">

            <option value="${medicine.medicineId}">

                ${medicine.medicineName}

            </option>

        </c:forEach>

    </select>

    <br>
    <br>


    <!-- DOSAGE -->

    <label>Dosage:</label>

    <input type="text"
           name="dosage"
           maxlength="100"
           required>

    <br>
    <br>


    <!-- INSTRUCTIONS -->

    <label>Instructions:</label>

    <br>

    <textarea name="instructions"
              rows="5"
              cols="50"
              maxlength="500"
              required></textarea>

    <br>
    <br>


    <!-- DATE -->

    <label>Prescription Date:</label>

    <input type="date"
           name="prescriptionDate"
           required>

    <br>
    <br>


    <button type="submit">
        Write Prescription
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/doctor">
    Back to Dashboard
</a>

</body>

</html>