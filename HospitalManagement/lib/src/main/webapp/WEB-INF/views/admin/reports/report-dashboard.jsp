<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Hospital Reports Dashboard</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        h1 {
            margin-bottom: 30px;
        }

        h2 {
            margin-top: 25px;
        }

        .report-section {
            margin-bottom: 30px;
        }

        ul {
            padding-left: 25px;
        }

        li {
            margin: 10px 0;
        }

        a {
            text-decoration: none;
            color: #0066cc;
        }

        a:hover {
            text-decoration: underline;
        }

    </style>

</head>

<body>

<h1>Hospital Reports Dashboard</h1>

<hr>


<!-- ========================================================= -->
<!-- PATIENT REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Patient Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/admitted-patients">
                Admitted Patients
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/patients-by-department">
                Patients by Department
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/patients-by-age">
                Patients by Age
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/patients-by-ward">
                Patients by Ward
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/grouped-patients-department">
                Patients Grouped by Department
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/patients-per-ward">
                Patients Per Ward
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/patient-names">
                Patient Names
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/immutable-patients">
                Immutable Patient List
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/patient-lookup">
                Patient Lookup
            </a>
        </li>

    </ul>

</div>


<!-- ========================================================= -->
<!-- DOCTOR REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Doctor Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/available-doctors">
                Available Doctors
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/doctors-by-experience">
                Doctors by Experience
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/highest-consultation-fee">
                Highest Consultation Fee
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/lowest-consultation-fee">
                Lowest Consultation Fee
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/doctors-by-specialization">
                Doctors by Specialization
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/appointments-per-doctor">
                Appointments Per Doctor
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/most-consulted-doctor">
                Most Consulted Doctor
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/distinct-specializations">
                Distinct Specializations
            </a>
        </li>

    </ul>

</div>


<!-- ========================================================= -->
<!-- APPOINTMENT REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Appointment Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/pending-appointments">
                Pending Appointments
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/appointments-by-status">
                Appointments by Status
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/earliest-appointment">
                Earliest Appointment
            </a>
        </li>

    </ul>

</div>


<!-- ========================================================= -->
<!-- ADMISSION REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Admission Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/latest-discharge">
                Latest Discharge
            </a>
        </li>

    </ul>

</div>


<!-- ========================================================= -->
<!-- BILL REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Bill Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/top-expensive-bills">
                Top 5 Expensive Bills
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/total-revenue">
                Total Hospital Revenue
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/average-bill">
                Average Bill Amount
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/pharmacy-sales">
                Total Pharmacy Sales
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/overdue-bills">
                Overdue Bills
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/paid-unpaid-bills">
                Paid / Unpaid Bills
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/bill-summary">
                Bill Summary Statistics
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/department-revenue">
                Department Revenue
            </a>
        </li>

    </ul>

</div>


<!-- ========================================================= -->
<!-- PHARMACY REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Pharmacy Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/unavailable-medicines">
                Unavailable Medicines
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/distinct-medicine-manufacturers">
                Distinct Medicine Manufacturers
            </a>
        </li>
        <li>
    <a href="${pageContext.request.contextPath}/admin/reports/pharmacy-sales">
        Total Pharmacy Sales
    </a>
</li>
<a href="${pageContext.request.contextPath}/admin/reports/medicine-stock">
    Medicine Stock
</a>

    </ul>

</div>


<!-- ========================================================= -->
<!-- WARD / BED REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Ward / Bed Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/icu-bed-availability">
                ICU Bed Availability
            </a>
        </li>

    </ul>

</div>


<!-- ========================================================= -->
<!-- LABORATORY REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Laboratory Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/test-reports-status">
                Laboratory Test Report Status
            </a>
        </li>

    </ul>

</div>


<!-- ========================================================= -->
<!-- JAVA STREAM REPORTS -->
<!-- ========================================================= -->

<div class="report-section">

    <h2>Java Stream Reports</h2>

    <ul>

        <li>
            <a href="${pageContext.request.contextPath}/admin/reports/stream-performance">
                Sequential vs Parallel Stream Performance
            </a>
        </li>

    </ul>

</div>


</body>
</html>