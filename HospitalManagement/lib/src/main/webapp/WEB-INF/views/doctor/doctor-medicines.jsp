<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Medicines</title>

</head>

<body>

<h1>Medicine Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/doctor/medicines/add">
    Add New Medicine
</a>

<br>
<br>

<c:choose>

    <c:when test="${empty medicines}">

        <p>No medicines found.</p>

    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>

                <th>Medicine ID</th>

                <th>Medicine Name</th>

                <th>Manufacturer</th>

                <th>Price</th>

            </tr>


            <c:forEach var="medicine"
                       items="${medicines}">

                <tr>

                    <td>
                        ${medicine.medicineId}
                    </td>

                    <td>
                        ${medicine.medicineName}
                    </td>

                    <td>
                        ${medicine.manufacturer}
                    </td>

                    <td>
                        ${medicine.price}
                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

<br>

<a href="${pageContext.request.contextPath}/doctor">
    Back to Dashboard
</a>

</body>

</html>