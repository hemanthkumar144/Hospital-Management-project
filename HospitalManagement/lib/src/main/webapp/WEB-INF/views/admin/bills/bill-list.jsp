<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bill Management</title>
</head>

<body>

<h1>Bill Management</h1>

<hr>

<a href="${pageContext.request.contextPath}/admin/bills/add">
    Generate Bill
</a>

<br><br>

<a href="${pageContext.request.contextPath}/admin/dashboard">
    Back to Admin Dashboard
</a>

<hr>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:choose>

    <c:when test="${empty bills}">
        <p>No bills found.</p>
    </c:when>

    <c:otherwise>

        <table border="1"
               cellpadding="10"
               cellspacing="0">

            <tr>
                <th>Bill ID</th>
                <th>Patient ID</th>
                <th>Bill Date</th>
                <th>Consultation</th>
                <th>Medicine</th>
                <th>Laboratory</th>
                <th>Bed</th>
                <th>Total</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            <c:forEach var="bill"
                       items="${bills}">

                <tr>

                    <td>
                        ${bill.billId}
                    </td>

                    <td>
                        ${bill.patient.patientId}
                    </td>

                    <td>
                        ${bill.billDate}
                    </td>

                    <td>
                        ${bill.consultationFee}
                    </td>

                    <td>
                        ${bill.medicineCharges}
                    </td>

                    <td>
                        ${bill.laboratoryCharges}
                    </td>

                    <td>
                        ${bill.bedCharges}
                    </td>

                    <td>
                        ${bill.totalAmount}
                    </td>

                    <td>
                        ${bill.status}
                    </td>

                    <td>

                        <a href="${pageContext.request.contextPath}/admin/bills/view/${bill.billId}">
                            View
                        </a>

                        <c:if test="${bill.status == 'PENDING'}">

                            |

                            <a href="${pageContext.request.contextPath}/admin/bills/pay/${bill.billId}"
                               onclick="return confirm('Mark this bill as paid?');">
                                Mark Paid
                            </a>

                            |

                            <a href="${pageContext.request.contextPath}/admin/bills/cancel/${bill.billId}"
                               onclick="return confirm('Cancel this bill?');">
                                Cancel
                            </a>

                        </c:if>

                        |

                        <a href="${pageContext.request.contextPath}/admin/bills/delete/${bill.billId}"
                           onclick="return confirm('Delete this bill?');">
                            Delete
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>

    </c:otherwise>

</c:choose>

</body>
</html>