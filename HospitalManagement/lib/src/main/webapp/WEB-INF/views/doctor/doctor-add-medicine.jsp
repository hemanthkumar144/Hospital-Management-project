<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Add Medicine</title>

</head>

<body>

<h1>Add Medicine</h1>

<hr>

<form method="post"
      action="${pageContext.request.contextPath}/doctor/medicines/add">

    <table>

        <tr>

            <td>
                Medicine Name:
            </td>

            <td>

                <input type="text"
                       name="medicineName"
                       required>

            </td>

        </tr>


        <tr>

            <td>
                Manufacturer:
            </td>

            <td>

                <input type="text"
                       name="manufacturer"
                       required>

            </td>

        </tr>


        <tr>

            <td>
                Price:
            </td>

            <td>

                <input type="number"
                       name="price"
                       step="0.01"
                       min="0"
                       required>

            </td>

        </tr>


        <tr>

            <td colspan="2">

                <button type="submit">
                    Add Medicine
                </button>

            </td>

        </tr>

    </table>

</form>

<br>

<a href="${pageContext.request.contextPath}/doctor/medicines">
    Back to Medicines
</a>

</body>

</html>