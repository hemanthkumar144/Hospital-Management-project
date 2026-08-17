<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Laboratory Test</title>
</head>

<body>

<h1>Add Laboratory Test</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/laboratory/add">

    <label>Test Name:</label>

    <input type="text"
           name="testName"
           required
           maxlength="100">

    <br><br>


    <label>Test Description:</label>

    <br>

    <textarea name="testDescription"
              rows="5"
              cols="50"
              required></textarea>

    <br><br>


    <label>Test Charge:</label>

    <input type="number"
           name="testCharge"
           step="0.01"
           min="0.01"
           required>

    <br><br>


    <label>Test Type:</label>

    <select name="testType" required>

        <option value="">-- Select Test Type --</option>

        <option value="BLOOD">BLOOD</option>
        <option value="XRAY">XRAY</option>
        <option value="MRI">MRI</option>
        <option value="URINE">URINE</option>
        <option value="CT">CT</option>

    </select>

    <br><br>

    <button type="submit">
        Add Laboratory Test
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/laboratory">
    Back to Laboratory List
</a>

</body>
</html>