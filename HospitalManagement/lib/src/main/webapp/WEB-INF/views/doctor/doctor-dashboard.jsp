<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Doctor Dashboard</title>

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
            background: #1976d2;
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
            color: #1976d2;

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
            color: #1976d2;

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

            background: #1976d2;

            color: white;

            padding: 10px 18px;

            border-radius: 6px;

            font-size: 14px;
        }

        .btn:hover {
            background: #125ca1;
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

    <a href="${pageContext.request.contextPath}/doctor/logout"
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

        <h1>Doctor Dashboard</h1>

        <p>
            Welcome Doctor. Manage your appointments,
            patients, prescriptions and medicines from here.
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
                View your upcoming patient appointments.
            </p>

            <a href="${pageContext.request.contextPath}/doctor/appointments"
               class="btn">
                View Appointments
            </a>

        </div>


        <!-- =============================================
             APPOINTMENTS BY DATE RANGE
             ============================================= -->

        <div class="card">

            <h3>Appointments by Date</h3>

            <p>
                View appointments within a selected
                date range.
            </p>

            <a href="${pageContext.request.contextPath}/doctor/appointments"class="btn">
    		View by Date
			</a>

        </div>


        <!-- =============================================
             CANCEL APPOINTMENT
             ============================================= -->

        <div class="card">

            <h3>Cancel Appointment</h3>

            <p>
                Cancel an appointment when required.
            </p>

            <a href="${pageContext.request.contextPath}/doctor/appointments"
   class="btn">
    Cancel Appointment
</a>

        </div>


        <!-- =============================================
             VIEW PATIENT
             ============================================= -->

        <div class="card">

            <h3>View Patient</h3>

            <p>
                Search and view individual patient details.
            </p>

            <a href="${pageContext.request.contextPath}/doctor/patient"
   class="btn">
    View Patient
</a>

        </div>


        <!-- =============================================
             LIST PATIENTS
             ============================================= -->

        <div class="card">

            <h3>List Patients</h3>

            <p>
                View the list of patients in the hospital.
            </p>

            <a href="${pageContext.request.contextPath}/doctor/patients"
               class="btn">
                List Patients
            </a>

        </div>


        <!-- =============================================
             WRITE PRESCRIPTION
             ============================================= -->

        <div class="card">

            <h3>Write Prescription</h3>

            <p>
                Create prescriptions for your patients.
            </p>

           <a href="${pageContext.request.contextPath}/doctor/prescription/add"
   class="btn">
    Write Prescription
</a>

        </div>


        <!-- =============================================
             MEDICINE MANAGEMENT
             ============================================= -->

        <div class="card">

            <h3>Medicine Management</h3>

            <p>
                View and manage medicines available
                in the hospital.
            </p>

            <a href="${pageContext.request.contextPath}/doctor/medicines"
               class="btn">
                Manage Medicines
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