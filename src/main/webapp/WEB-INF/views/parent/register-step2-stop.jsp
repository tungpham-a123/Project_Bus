<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký Tuyến Xe - Bước 2</title>
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

            .step.completed .step-circle {
                background-color: #28a745;
                border-color: #28a745;
                color: white;
            }

            .step.completed .step-label {
                color: #28a745;
            }

            /* Form Card Styles */
            .form-card {
                background-color: #ffffff;
                padding: 2rem 2.5rem;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            }

            .form-card h1 {
                text-align: center;
                margin-bottom: 0.5rem;
                color: #343a40;
            }

            .form-card .info {
                text-align: center;
                margin-bottom: 2rem;
                font-weight: 400;
                color: #6c757d;
            }

            .info-summary {
                background-color: #e9ecef;
                padding: 0.75rem;
                border-radius: 8px;
                margin-bottom: 1.5rem;
                text-align: center;
            }

            /* Stop Selector Styles */
            .stop-selector {
                display: flex;
                flex-direction: column;
                gap: 1rem;
            }

            .stop-option {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 1rem;
                border: 2px solid #ced4da;
                border-radius: 8px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .stop-option:hover {
                border-color: #80bdff;
            }

            .stop-option input[type="radio"] {
                display: none;
            }

            .stop-option input[type="radio"]:checked + .stop-details {
                font-weight: 600;
                color: #0056b3;
            }

            .stop-option input[type="radio"]:checked + .stop-details + .stop-time {
                color: #0056b3;
            }

            .stop-option input[type="radio"]:checked ~ .radio-check::after {
                opacity: 1;
            }

            .stop-option.selected {
                border-color: #007bff;
                background-color: #f0f8ff;
            }

            .stop-details, .stop-time {
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }

            .form-actions {
                margin-top: 2rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
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
                <div class="step completed">
                    <div class="step-circle"><i class="fas fa-check"></i></div>
                    <div class="step-label">Chọn tuyến</div>
                </div>
                <div class="step active">
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
                <p class="info">Cho bé: <strong>${student.fullName}</strong></p>
                <div class="info-summary">
                    Tuyến đã chọn: <strong>${route.routeName}</strong>
                </div>

                <form action="<c:url value='/parent/register/complete'/>" method="post">
                    <input type="hidden" name="studentId" value="${student.id}">
                    <input type="hidden" name="routeId" value="${route.id}">

                    <div class="form-group">
                        <label style="margin-bottom: 1rem; font-weight: 500;">Vui lòng chọn điểm đón:</label>
                        <div class="stop-selector">
                            <c:forEach var="routeStop" items="${stops}" varStatus="loop">
                                <c:if test="${routeStop.stop.stopName != 'Trường học'}">
                                    <label for="stop-${loop.index}" class="stop-option">
                                        <input type="radio" id="stop-${loop.index}" name="stopId" value="${routeStop.stop.id}" required 
                                               onclick="document.querySelectorAll('.stop-option').forEach(el => el.classList.remove('selected')); this.parentElement.classList.add('selected');">
                                        <div class="stop-details">
                                            <i class="fas fa-map-marker-alt"></i>
                                            <span>${routeStop.stop.stopName}</span>
                                        </div>
                                        <div class="stop-time">
                                            <i class="fas fa-clock"></i>
                                            <span>Đón lúc: ${routeStop.estimatedPickupTime}</span>
                                        </div>
                                    </label>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="form-actions">
                        <div>
                            <a href="javascript:history.back()" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
                        </div>
                        <div>
                            <a href="<c:url value='/parent/dashboard'/>" style="margin-right: 10px; text-decoration: none; color: #6c757d;">Hủy</a>
                            <button type="submit" class="btn btn-primary">Hoàn tất Đăng ký <i class="fas fa-check-circle"></i></button>
                        </div>
                    </div>
                </form>
            </div>
        </main>

    </body>
</html>