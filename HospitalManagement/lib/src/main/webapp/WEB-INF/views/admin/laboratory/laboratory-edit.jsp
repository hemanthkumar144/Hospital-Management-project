<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Laboratory Test</title>
</head>

<body>

<h1>Edit Laboratory Test</h1>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/laboratory/edit">

    <input type="hidden"
           name="testId"
           value="${test.testId}">


    <label>Test Name:</label>

    <input type="text"
           name="testName"
           value="${test.testName}"
           maxlength="100"
           required>

    <br><br>


    <label>Test Description:</label>

    <br>

    <textarea name="testDescription"
              rows="5"
              cols="50"
              required>${test.testDescription}</textarea>

    <br><br>


    <label>Test Charge:</label>

    <input type="number"
           name="testCharge"
           value="${test.testCharge}"
           step="0.01"
           min="0.01"
           required>

    <br><br>


    <label>Test Type:</label>

    <select name="testType" required>

        <option value="BLOOD"
            ${test.testType == 'BLOOD'
                ? 'selected'
                : ''}>
            BLOOD
        </option>

        <option value="XRAY"
            ${test.testType == 'XRAY'
                ? 'selected'
                : ''}>
            XRAY
        </option>

        <option value="MRI"
            ${test.testType == 'MRI'
                ? 'selected'
                : ''}>
            MRI
        </option>

        <option value="URINE"
            ${test.testType == 'URINE'
                ? 'selected'
                : ''}>
            URINE
        </option>

        <option value="CT"
            ${test.testType == 'CT'
                ? 'selected'
                : ''}>
            CT
        </option>

    </select>

    <br><br>

    <button type="submit">
        Update Laboratory Test
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/admin/laboratory">
    Back to Laboratory List
</a>

</body>
</html>