<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Admin Dashboard</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f4f6f9;
        }

        .header {
            background-color: #1f2937;
            color: white;
            padding: 20px 30px;
        }

        .header h1 {
            margin: 0;
        }

        .container {
            padding: 30px;
        }

        .section-title {
            margin-top: 25px;
            margin-bottom: 15px;
        }

        .menu-grid {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(220px, 1fr));
            gap: 20px;
        }

        .menu-card {
            background-color: white;
            padding: 25px;
            border-radius: 8px;
            text-decoration: none;
            color: #222;
            box-shadow:
                0 2px 8px rgba(0,0,0,0.10);
        }

        .menu-card:hover {
            box-shadow:
                0 4px 14px rgba(0,0,0,0.18);
        }

        .menu-card h3 {
            margin-top: 0;
        }

        .menu-card p {
            color: #666;
        }

        .logout {
            display: inline-block;
            margin-top: 30px;
            padding: 10px 20px;
            background-color: #dc3545;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

    </style>

</head>

<body>

<div class="header">

    <h1>Hospital ERP - Admin Dashboard</h1>

    <p>
        Welcome, ${sessionScope.username}
    </p>

</div>


<div class="container">

    <h2 class="section-title">
        Hospital Management
    </h2>


    <div class="menu-grid">


        <!-- PATIENT -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/patients">

            <h3>Patient Management</h3>

            <p>
                View and manage patients.
            </p>

        </a>


        <!-- DOCTOR -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/doctors">

            <h3>Doctor Management</h3>

            <p>
                Add, view, update and manage doctors.
            </p>

        </a>


        <!-- NURSE -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/nurses">

            <h3>Nurse Management</h3>

            <p>
                Add, view, update and manage nurses.
            </p>

        </a>


        <!-- DEPARTMENT -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/departments">

            <h3>Department Management</h3>

            <p>
                Manage hospital departments.
            </p>

        </a>


        <!-- APPOINTMENT -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/appointments">

            <h3>Appointment Management</h3>

            <p>
                Book, view, update and cancel appointments.
            </p>

        </a>


        <!-- PRESCRIPTION -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/prescriptions">

            <h3>Prescription Management</h3>

            <p>
                Manage patient prescriptions.
            </p>

        </a>


        <!-- MEDICINE -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/medicines">

            <h3>Medicine Management</h3>

            <p>
                Manage medicines.
            </p>

        </a>


        <!-- LABORATORY -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/laboratory">

            <h3>Laboratory</h3>

            <p>
                Manage laboratory tests.
            </p>

        </a>


        <!-- PATIENT TEST -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/patient-tests">

            <h3>Patient Tests</h3>

            <p>
                Manage patient laboratory tests.
            </p>

        </a>


        <!-- WARD -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/wards">

            <h3>Ward Management</h3>

            <p>
                Manage hospital wards.
            </p>

        </a>


        <!-- BED -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/beds">

            <h3>Bed Management</h3>

            <p>
                Manage hospital beds.
            </p>

        </a>


        <!-- ADMISSION -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/admissions">

            <h3>Admission Management</h3>

            <p>
                Admit and discharge patients.
            </p>

        </a>


        <!-- BILL -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/bills">

            <h3>Bill Management</h3>

            <p>
                Manage patient bills.
            </p>

        </a>


        <!-- PAYMENT -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/payments">

            <h3>Payment Management</h3>

            <p>
                Manage patient payments.
            </p>

        </a>


        <!-- REPORTS -->

        <a class="menu-card"
           href="${pageContext.request.contextPath}/admin/reports">

            <h3>Reports</h3>

            <p>
                Generate hospital reports.
            </p>

        </a>


    </div>


    <a class="logout"
       href="${pageContext.request.contextPath}/logout">

        Logout

    </a>

</div>

</body>

</html>