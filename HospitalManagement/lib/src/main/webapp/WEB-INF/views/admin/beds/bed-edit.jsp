<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Bed</title>
</head>

<body>

<h1>Edit Bed</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/beds/edit">

    <input type="hidden"
           name="bedId"
           value="${bed.bedId}">


    <label>Ward:</label>

    <select name="wardId" required>

        <c:forEach var="ward"
                   items="${wards}">

            <c:if test="${ward.active}">

                <option value="${ward.wardId}"
                    ${ward.wardId == bed.ward.wardId
                        ? 'selected'
                        : ''}>

                    ${ward.wardName}
                    -
                    ${ward.wardId}

                </option>

            </c:if>

        </c:forEach>

    </select>

    <br><br>


    <label>Availability:</label>

    <select name="availability"
            id="availability"
            required
            onchange="togglePatient()">

        <option value="AVAILABLE"
            ${bed.availability == 'AVAILABLE'
                ? 'selected'
                : ''}>
            AVAILABLE
        </option>

        <option value="OCCUPIED"
            ${bed.availability == 'OCCUPIED'
                ? 'selected'
                : ''}>
            OCCUPIED
        </option>

    </select>

    <br><br>


    <div id="patientSection">

        <label>Patient:</label>

        <select name="patientId"
                id="patientId">

            <option value="">
                -- Select Patient --
            </option>

            <c:forEach var="patient"
                       items="${patients}">

                <c:if test="${patient.active}">

                    <option value="${patient.patientId}"
                        ${not empty bed.patient
                            && patient.patientId
                            == bed.patient.patientId
                            ? 'selected'
                            : ''}>

                        ${patient.patientId}
                        -
                        ${patient.patientName}

                    </option>

                </c:if>

            </c:forEach>

        </select>

        <br><br>

    </div>


    <button type="submit">
        Update Bed
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/beds">
    Back to Bed List
</a>


<script>

function togglePatient() {

    var availability =
        document.getElementById("availability").value;

    var patientSection =
        document.getElementById("patientSection");

    var patient =
        document.getElementById("patientId");

    if (availability === "OCCUPIED") {

        patientSection.style.display = "block";
        patient.required = true;

    } else {

        patientSection.style.display = "none";
        patient.required = false;
        patient.value = "";
    }
}

window.onload = function() {
    togglePatient();
};

</script>

</body>
</html>