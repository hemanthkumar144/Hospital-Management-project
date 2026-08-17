<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Prescription</title>
</head>

<body>

<h1>Edit Prescription</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/prescriptions/edit">

    <input type="hidden"
           name="prescriptionId"
           value="${prescription.prescriptionId}">


    <label>Patient:</label>

    <select name="patientId" required>

        <c:forEach var="patient"
                   items="${patients}">

            <option value="${patient.patientId}"
                ${patient.patientId == prescription.patient.patientId
                    ? 'selected'
                    : ''}>

                ${patient.patientId}

            </option>

        </c:forEach>

    </select>

    <br><br>


    <label>Doctor:</label>

    <select name="doctorId" required>

        <c:forEach var="doctor"
                   items="${doctors}">

            <option value="${doctor.staffId}"
                ${doctor.staffId == prescription.doctor.staffId
                    ? 'selected'
                    : ''}>

                ${doctor.staffId} - ${doctor.name}

            </option>

        </c:forEach>

    </select>

    <br><br>


    <label>Prescription Date:</label>

    <input type="date"
           name="prescriptionDate"
           value="${prescription.prescriptionDate}"
           required>

    <br><br>


    <label>Instructions:</label>

    <br>

    <textarea name="instructions"
              rows="5"
              cols="50"
              maxlength="255"
              required>${prescription.instructions}</textarea>

    <br><br>


    <h2>Medicines</h2>

    <div id="medicineContainer">

        <c:forEach var="medicine"
                   items="${prescription.medicines}">

            <div class="medicine-row">

                <label>Medicine:</label>

                <select name="medicineId">

                    <option value="">
                        -- Select Medicine --
                    </option>

                    <c:forEach var="availableMedicine"
                               items="${medicines}">

                        <option
                            value="${availableMedicine.medicineId}"
                            ${availableMedicine.medicineId
                                == medicine.medicineId
                                ? 'selected'
                                : ''}>

                            ${availableMedicine.medicineId}

                        </option>

                    </c:forEach>

                </select>

                <br>

                <label>Dosage:</label>

                <input type="text"
                       name="dosage"
                       value="${medicine.dosage}"
                       maxlength="100">

                <br>

                <label>Quantity:</label>

                <input type="number"
                       name="quantity"
                       value="${medicine.quantity}"
                       min="1">

                <br><br>

            </div>

        </c:forEach>

    </div>


    <h2>Laboratory Tests</h2>

    <c:forEach var="prescriptionTest"
               items="${prescription.tests}">

        <select name="testId">

            <option value="">
                -- Select Test --
            </option>

            <c:forEach var="availableTest"
                       items="${tests}">

                <option
                    value="${availableTest.testId}"
                    ${availableTest.testId
                        == prescriptionTest.testId
                        ? 'selected'
                        : ''}>

                    ${availableTest.testId}

                </option>

            </c:forEach>

        </select>

        <br><br>

    </c:forEach>


    <button type="submit">
        Update Prescription
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/prescriptions">
    Back to Prescription List
</a>

</body>
</html>