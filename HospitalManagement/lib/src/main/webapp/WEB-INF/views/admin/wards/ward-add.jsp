<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Ward</title>
</head>

<body>

<h1>Add Ward</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/wards/add">

    <label>Ward Name:</label>

    <input type="text"
           name="wardName"
           maxlength="50"
           required>

    <br><br>


  <label>Ward Type:</label>

<select name="wardType" required>

    <option value="">
        -- Select Ward Type --
    </option>

    <option value="GENERAL">
        General
    </option>

    <option value="ICU">
        ICU
    </option>

    <option value="PEDIATRIC">
        Pediatric
    </option>

    <option value="PRIVATE">
        Private
    </option>

</select>


    <label>Bed Charge:</label>

    <input type="number"
           name="bedCharge"
           step="0.01"
           min="0.01"
           required>

    <br><br>


    <button type="submit">
        Add Ward
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/wards">
    Back to Ward List
</a>

</body>
</html>