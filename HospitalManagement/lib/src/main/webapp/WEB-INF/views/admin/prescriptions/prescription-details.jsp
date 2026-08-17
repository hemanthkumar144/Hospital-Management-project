<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prescription Details</title>
</head>

<body>

<h1>Prescription Details</h1>

<hr>

<table border="1"
       cellpadding="10"
       cellspacing="0">

    <tr>
        <th>Prescription ID</th>
        <td>${prescription.prescriptionId}</td>
    </tr>

    <tr>
        <th>Patient ID</th>
        <td>${prescription.patient.patientId}</td>
    </tr>

    <tr>
        <th>Doctor ID</th>
        <td>${prescription.doctor.staffId}</td>
    </tr>

    <tr>
        <th>Prescription Date</th>
        <td>${prescription.prescriptionDate}</td>
    </tr>

    <tr>
        <th>Instructions</th>
        <td>${prescription.instructions}</td>
    </tr>

    <tr>
        <th>Status</th>

        <td>

            <c:choose>

                <c:when test="${prescription.active}">
                    Active
                </c:when>

                <c:otherwise>
                    Inactive
                </c:otherwise>

            </c:choose>

        </td>

    </tr>

</table>

<br>

<h2>Medicines</h2>

<c:choose>

    <c:when test="${empty prescription.medicines}">
        <p>No medicines.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="8"
               cellspacing="0">

            <tr>
                <th>Medicine ID</th>
                <th>Dosage</th>
                <th>Quantity</th>
            </tr>

            <c:forEach var="medicine"
                       items="${prescription.medicines}">

                <tr>

                    <td>
                        ${medicine.medicineId}
                    </td>

                    <td>
                        ${medicine.dosage}
                    </td>

                    <td>
                        ${medicine.quantity}
                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<h2>Laboratory Tests</h2>

<c:choose>

    <c:when test="${empty prescription.tests}">
        <p>No laboratory tests.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="8"
               cellspacing="0">

            <tr>
                <th>Test ID</th>
            </tr>

            <c:forEach var="test"
                       items="${prescription.tests}">

                <tr>

                    <td>
                        ${test.testId}
                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/admin/prescriptions/edit/${prescription.prescriptionId}">
    Edit Prescription
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/prescriptions">
    Back to Prescription List
</a>

</body>
</html>