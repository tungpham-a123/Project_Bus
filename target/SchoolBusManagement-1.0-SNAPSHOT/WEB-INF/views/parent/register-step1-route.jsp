<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký Tuyến Xe - Bước 1</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Poppins', sans-serif;
                background-color: #f4f7f6;
                color: #333;
            }

            /* Navbar Styles (Reused) */
            .navbar {
                background-color: #ffffff;
                padding: 1rem 2rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .navbar-brand {
                font-size: 1.5rem;
                font-weight: 600;
                color: #0056b3;
            }

            .navbar-user {
                font-weight: 500;
            }

            .btn-logout {
                text-decoration: none;
                background-color: #dc3545;
                color: white;
                padding: 0.5rem 1rem;
                border-radius: 5px;
                margin-left: 1rem;
                transition: background-color 0.3s;
            }

            .btn-logout:hover {
                background-color: #c82333;
            }

            /* Main Container */
            .container {
                max-width: 800px;
                margin: 2rem auto;
                padding: 0 1rem;
            }

            /* Stepper/Wizard Styles */
            .stepper {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 2rem;
                position: relative;
            }

            .stepper::before {
                content: '';
                position: absolute;
                top: 50%;
                left: 0;
                right: 0;
                height: 2px;
                background-color: #e0e0e0;
                transform: translateY(-50%);
                z-index: 1;
            }

            .step {
                display: flex;
                flex-direction: column;
                align-items: center;
                z-index: 2;
                background-color: #f4f7f6;
                padding: 0 1rem;
            }

            .step-circle {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: #e0e0e0;
                color: #999;
                display: flex;
                justify-content: center;
                align-items: center;
                font-weight: 600;
                border: 2px solid #e0e0e0;
                transition: all 0.3s ease;
            }

            .step-label {
                margin-top: 0.5rem;
                font-size: 0.9rem;
                color: #999;
            }

            .step.active .step-circle {
                background-color: #007bff;
                border-color: #007bff;
                color: white;
            }

            .step.active .step-label {
                color: #007bff;
                font-weight: 500;
            }

            /* Form Card Styles */
            .form-card {
                background-color: #ffffff;
                padding: 2rem 2.5rem;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            }

            .form-card h1, .form-card h3 {
                text-align: center;
                margin-bottom: 0.5rem;
                color: #343a40;
            }

            .form-card h3 {
                margin-bottom: 2rem;
                font-weight: 400;
                color: #6c757d;
            }

            /* Form Element Styles */
            .form-group {
                margin-bottom: 1.5rem;
            }

            .form-group label {
                display: block;
                margin-bottom: 0.5rem;
                font-weight: 500;
            }

            .form-group select {
                width: 100%;
                padding: 0.75rem 1rem;
                border: 1px solid #ced4da;
                border-radius: 8px;
                font-size: 1rem;
                font-family: 'Poppins', sans-serif;
                background-color: #fff;
            }

            .form-group select:focus {
                outline: none;
                border-color: #80bdff;
                box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
            }

            .form-actions {
                margin-top: 2rem;
                display: flex;
                justify-content: flex-end;
                align-items: center;
                gap: 1rem;
            }

            .btn {
                padding: 0.7rem 1.5rem;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                font-weight: 500;
                font-size: 1rem;
                transition: all 0.3s;
            }

            .btn-primary {
                background-color: #007bff;
                color: white;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .btn-secondary {
                background-color: #6c757d;
                color: white;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
            }

        </style>
    </head>
    <body>

        <header class="navbar">
            <div class="navbar-brand">
                <i class="fas fa-bus-alt" style="color: #FFC107;"></i> SchoolBus
            </div>
            <div>
                <span class="navbar-user">Xin chào, <strong>${sessionScope.user.fullName}</strong></span>
                <a href="<c:url value='/logout' />" class="btn-logout">
                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                </a>
            </div>
        </header>

        <main class="container">
            <div class="stepper">
                <div class="step active">
                    <div class="step-circle">1</div>
                    <div class="step-label">Chọn tuyến</div>
                </div>
                <div class="step">
                    <div class="step-circle">2</div>
                    <div class="step-label">Chọn điểm đón</div>
                </div>
                <div class="step">
                    <div class="step-circle">3</div>
                    <div class="step-label">Xác nhận</div>
                </div>
            </div>

            <div class="form-card">
                <h1>Đăng Ký Tuyến Xe</h1>
                <h3>Cho bé: <strong>${student.fullName}</strong></h3>

                <form action="<c:url value='/parent/register/step2'/>" method="post">
                    <input type="hidden" name="studentId" value="${student.id}">

                    <div class="form-group">
                        <label for="routeId"><i class="fas fa-route"></i> Vui lòng chọn tuyến xe</label>
                        <select id="routeId" name="routeId" required>
                            <option value="" disabled selected>-- Chọn một tuyến --</option>
                            <c:forEach var="route" items="${routes}">
                                <option value="${route.id}">${route.routeName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-actions">
                        <a href="<c:url value='/parent/dashboard'/>" class="btn btn-secondary">Hủy</a>
                        <button type="submit" class="btn btn-primary">Tiếp theo <i class="fas fa-arrow-right"></i></button>
                    </div>
                </form>
            </div>
        </main>

    </body>
</html>