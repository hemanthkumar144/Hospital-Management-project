<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Ward</title>
</head>

<body>

<h1>Edit Ward</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/wards/edit">

    <input type="hidden"
           name="wardId"
           value="${ward.wardId}">


    <label>Ward Name:</label>

    <input type="text"
           name="wardName"
           value="${ward.wardName}"
           maxlength="50"
           required>

    <br><br>

<label>Ward Type:</label>

<select name="wardType" required>

    <option value="GENERAL"
        ${ward.wardType == 'GENERAL'
            ? 'selected'
            : ''}>
        General
    </option>

    <option value="ICU"
        ${ward.wardType == 'ICU'
            ? 'selected'
            : ''}>
        ICU
    </option>

    <option value="PEDIATRIC"
        ${ward.wardType == 'PEDIATRIC'
            ? 'selected'
            : ''}>
        Pediatric
    </option>

    <option value="PRIVATE"
        ${ward.wardType == 'PRIVATE'
            ? 'selected'
            : ''}>
        Private
    </option>

</select>
    

    <label>Bed Charge:</label>

    <input type="number"
           name="bedCharge"
           value="${ward.bedCharge}"
           step="0.01"
           min="0.01"
           required>

    <br><br>


    <button type="submit">
        Update Ward
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/wards">
    Back to Ward List
</a>

</body>
</html>