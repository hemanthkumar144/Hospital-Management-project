<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Patient Dashboard</title>

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
            background: #6a1b9a;
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
            color: #6a1b9a;

            margin-bottom: 10px;
        }

        .welcome p {
            color: #666;

            font-size: 15px;
        }


        /* =========================
           PATIENT INFORMATION
           ========================= */

        .patient-info {
            background: white;

            padding: 20px 25px;

            border-radius: 10px;

            margin-bottom: 30px;

            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
        }

        .patient-info h3 {
            color: #6a1b9a;

            margin-bottom: 15px;
        }

        .patient-info p {
            margin: 7px 0;

            color: #555;
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
            color: #6a1b9a;

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

            background: #6a1b9a;

            color: white;

            padding: 10px 18px;

            border-radius: 6px;

            font-size: 14px;
        }

        .btn:hover {
            background: #4a148c;
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

    <a href="${pageContext.request.contextPath}/patient/logout"
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

        <h1>Patient Dashboard</h1>

        <p>
            Welcome. Manage your appointments,
            prescriptions, bills and payments from here.
        </p>

    </div>


    <!-- =================================================
         PATIENT INFORMATION
         ================================================= -->

    <div class="patient-info">

        <h3>Patient Information</h3>

        <p>
            <strong>Patient ID:</strong>
            ${patient.patientId}
        </p>

        <p>
            <strong>Name:</strong>
            ${patient.patientName}
        </p>

        <p>
            <strong>Age:</strong>
            ${patient.age}
        </p>

        <p>
            <strong>Gender:</strong>
            ${patient.gender}
        </p>

        <p>
            <strong>Phone:</strong>
            ${patient.phone}
        </p>

    </div>


    <!-- =================================================
         DASHBOARD CARDS
         ================================================= -->

    <div class="dashboard-grid">


        <!-- =============================================
             BOOK APPOINTMENT
             ============================================= -->

        <div class="card">

            <h3>Book Appointment</h3>

            <p>
                Schedule an appointment with a doctor.
            </p>

            <a href="${pageContext.request.contextPath}/patient/book-appointment"
               class="btn">
                Book Appointment
            </a>

        </div>


        <!-- =============================================
             VIEW APPOINTMENTS
             ============================================= -->

        <div class="card">

            <h3>My Appointments</h3>

            <p>
                View your scheduled appointments.
            </p>

            <a href="${pageContext.request.contextPath}/patient/appointments"
               class="btn">
                View Appointments
            </a>

        </div>


        <!-- =============================================
             PRESCRIPTIONS
             ============================================= -->

        <div class="card">

            <h3>My Prescriptions</h3>

            <p>
                View prescriptions given by your doctors.
            </p>

            <a href="${pageContext.request.contextPath}/patient/prescriptions"
               class="btn">
                View Prescriptions
            </a>

        </div>


        <!-- =============================================
             BILLS
             ============================================= -->

        <div class="card">

            <h3>My Bills</h3>

            <p>
                View your hospital bills and payment status.
            </p>

            <a href="${pageContext.request.contextPath}/patient/bills"
               class="btn">
                View Bills
            </a>

        </div>


        <!-- =============================================
             PAYMENT
             ============================================= -->

        <div class="card">

            <h3>Make Payment</h3>

            <p>
                Make payment for your pending hospital bill.
            </p>

            <a href="${pageContext.request.contextPath}/patient/bills"
               class="btn">
                Make Payment
            </a>

        </div>


        <!-- =============================================
             PROFILE
             ============================================= -->

        <div class="card">

            <h3>My Profile</h3>

            <p>
                View your personal and contact information.
            </p>

            <a href="${pageContext.request.contextPath}/patient/profile"
               class="btn">
                View Profile
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