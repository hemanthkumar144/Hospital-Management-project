<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Nurse Dashboard</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f4f7fb;
            color: #333;
        }

        /* =========================
           NAVBAR
           ========================= */

        .navbar {
            height: 70px;
            background: #00897b;
            color: white;

            display: flex;
            align-items: center;
            justify-content: space-between;

            padding: 0 40px;

            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
        }

        .navbar h2 {
            font-size: 23px;
        }

        .logout {
            text-decoration: none;
            color: white;

            background: #d32f2f;

            padding: 10px 20px;

            border-radius: 6px;

            font-size: 14px;
        }

        .logout:hover {
            background: #b71c1c;
        }


        /* =========================
           MAIN CONTAINER
           ========================= */

        .container {
            width: 90%;
            max-width: 1200px;

            margin: 40px auto;
        }


        /* =========================
           WELCOME
           ========================= */

        .welcome {
            background: white;

            padding: 30px;

            border-radius: 10px;

            margin-bottom: 30px;

            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
        }

        .welcome h1 {
            color: #00897b;

            margin-bottom: 10px;
        }

        .welcome p {
            color: #666;

            font-size: 15px;
        }


        /* =========================
           DASHBOARD GRID
           ========================= */

        .dashboard-grid {
            display: grid;

            grid-template-columns:
                    repeat(auto-fit, minmax(250px, 1fr));

            gap: 25px;
        }


        /* =========================
           CARD
           ========================= */

        .card {
            background: white;

            padding: 30px;

            border-radius: 10px;

            text-align: center;

            box-shadow:
                    0 2px 10px rgba(0, 0, 0, 0.08);

            transition: 0.2s;
        }

        .card:hover {
            transform: translateY(-4px);

            box-shadow:
                    0 5px 18px rgba(0, 0, 0, 0.15);
        }

        .card h3 {
            color: #00897b;

            margin-bottom: 12px;
        }

        .card p {
            color: #666;

            font-size: 14px;

            line-height: 1.5;

            min-height: 42px;

            margin-bottom: 20px;
        }


        /* =========================
           BUTTON
           ========================= */

        .btn {
            display: inline-block;

            text-decoration: none;

            background: #00897b;

            color: white;

            padding: 10px 18px;

            border-radius: 6px;

            font-size: 14px;
        }

        .btn:hover {
            background: #00695c;
        }


        /* =========================
           FOOTER
           ========================= */

        footer {
            text-align: center;

            margin-top: 50px;

            padding: 20px;

            color: #777;

            font-size: 13px;
        }

    </style>

</head>


<body>


<!-- =====================================================
     NAVBAR
     ===================================================== -->

<div class="navbar">

    <h2>Hospital Management System</h2>

    <a href="${pageContext.request.contextPath}/nurse/logout"
       class="logout">
        Logout
    </a>

</div>


<!-- =====================================================
     MAIN CONTENT
     ===================================================== -->

<div class="container">


    <!-- =================================================
         WELCOME
         ================================================= -->

    <div class="welcome">

        <h1>Nurse Dashboard</h1>

        <p>
            Welcome Nurse. Manage appointments, patients,
            prescriptions, admissions and hospital beds
            from here.
        </p>

    </div>


    <!-- =================================================
         DASHBOARD CARDS
         ================================================= -->

    <div class="dashboard-grid">


        <!-- =============================================
             VIEW APPOINTMENTS
             ============================================= -->

        <div class="card">

            <h3>View Appointments</h3>

            <p>
                View all scheduled patient appointments.
            </p>

            <a href="${pageContext.request.contextPath}/nurse/appointments"
               class="btn">
                View Appointments
            </a>

        </div>


        <!-- =============================================
             LIST PATIENTS
             ============================================= -->

        <div class="card">

            <h3>Patients</h3>

            <p>
                View the list of patients in the hospital.
            </p>

            <a href="${pageContext.request.contextPath}/nurse/patients"
               class="btn">
                View Patients
            </a>

        </div>


        <!-- =============================================
             VIEW PRESCRIPTIONS
             ============================================= -->

        <div class="card">

            <h3>Prescriptions</h3>

            <p>
                View prescriptions created for patients.
            </p>

            <a href="${pageContext.request.contextPath}/nurse/prescriptions"
               class="btn">
                View Prescriptions
            </a>

        </div>


        <!-- =============================================
             ADMIT PATIENT
             ============================================= -->

        <div class="card">

            <h3>Admit Patient</h3>

            <p>
                Admit a patient and assign an available bed.
            </p>

            <a href="${pageContext.request.contextPath}/nurse/admission/add"
               class="btn">
                Admit Patient
            </a>

        </div>


        <!-- =============================================
             VIEW ADMISSIONS
             ============================================= -->

        <div class="card">

            <h3>Admissions</h3>

            <p>
                View currently registered patient admissions.
            </p>

            <a href="${pageContext.request.contextPath}/nurse/admissions"
               class="btn">
                View Admissions
            </a>

        </div>


        <!-- =============================================
             DISCHARGE PATIENT
             ============================================= -->

        <div class="card">

            <h3>Discharge Patient</h3>

            <p>
                Discharge admitted patients and release
                their assigned beds.
            </p>

            <a href="${pageContext.request.contextPath}/nurse/admissions"
               class="btn">
                Discharge Patient
            </a>

        </div>


        <!-- =============================================
             MANAGE BEDS
             ============================================= -->

        <div class="card">

            <h3>Manage Beds</h3>

            <p>
                View hospital beds and their current status.
            </p>

            <a href="${pageContext.request.contextPath}/nurse/beds"
               class="btn">
                Manage Beds
            </a>

        </div>


    </div>

</div>


<!-- =====================================================
     FOOTER
     ===================================================== -->

<footer>

    Hospital Management System

</footer>


</body>

</html>