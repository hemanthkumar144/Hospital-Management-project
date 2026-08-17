<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Bed</title>
</head>

<body>

<h1>Add Bed</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/beds/add">

    <label>Ward:</label>

    <select name="wardId" required>

        <option value="">
            -- Select Ward --
        </option>

        <c:forEach var="ward"
                   items="${wards}">

            <c:if test="${ward.active}">

                <option value="${ward.wardId}">

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

        <option value="">
            -- Select Availability --
        </option>

        <option value="AVAILABLE">
            AVAILABLE
        </option>

        <option value="OCCUPIED">
            OCCUPIED
        </option>

    </select>

    <br><br>


    <div id="patientSection"
         style="display:none;">

        <label>Patient:</label>

        <select name="patientId"
                id="patientId">

            <option value="">
                -- Select Patient --
            </option>

            <c:forEach var="patient"
                       items="${patients}">

                <c:if test="${patient.active}">

                    <option value="${patient.patientId}">

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
        Add Bed
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

</script>

</body>
</html>