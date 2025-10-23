<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Trang Quản Lý Xe</title>
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
                max-width: 960px;
                margin: 2rem auto;
                padding: 0 1rem;
            }

            .page-title {
                margin-bottom: 1.5rem;
                font-size: 2rem;
                font-weight: 600;
                color: #343a40;
            }

            /* Trip Card Styles */
            .trip-card {
                background-color: #ffffff;
                border-radius: 12px;
                margin-bottom: 1.5rem;
                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
                border-left: 6px solid;
                display: flex;
                flex-direction: column;
            }

            /* Status colors for card border */
            .status-chua-bat-dau {
                border-color: #6c757d;
            }
            .status-dang-di-chuyen {
                border-color: #007bff;
            }
            .status-hoan-thanh {
                border-color: #28a745;
            }

            .card-body {
                padding: 1.5rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
            }

            .trip-details {
                flex-grow: 1;
            }

            .trip-details h3 {
                margin-top: 0;
                margin-bottom: 0.75rem;
                font-size: 1.3rem;
                font-weight: 600;
            }

            .info-row {
                display: flex;
                align-items: center;
                gap: 10px;
                color: #555;
                margin-bottom: 0.5rem;
            }

            .info-row i {
                color: #007bff;
                width: 20px;
                text-align: center;
            }

            .status-badge {
                padding: 0.25em 0.6em;
                font-size: 0.85em;
                font-weight: 600;
                border-radius: 50px;
                color: #fff;
            }

            .badge-secondary {
                background-color: #6c757d;
            }
            .badge-primary {
                background-color: #007bff;
            }
            .badge-success {
                background-color: #28a745;
            }

            .card-actions {
                padding: 0 1.5rem 1.5rem 1.5rem;
                text-align: right;
            }

            .btn {
                padding: 0.6rem 1.5rem;
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

            .empty-state {
                background-color: #fff;
                padding: 3rem;
                border-radius: 12px;
                text-align: center;
                color: #6c757d;
                border: 1px dashed #ced4da;
            }

        </style>
    </head>
    <body>

        <header class="navbar">
            <div class="navbar-brand">
                <i class="fas fa-clipboard-list" style="color: #17a2b8;"></i> Monitor Portal
            </div>
            <div>
                <span class="navbar-user">Xin chào, <strong>${sessionScope.user.fullName}</strong></span>
                <a href="<c:url value='/logout' />" class="btn-logout">
                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                </a>
            </div>
        </header>

        <main class="container">
            <h2 class="page-title">Các chuyến đi được phân công hôm nay</h2>

            <c:if test="${empty trips}">
                <div class="empty-state">
                    <p><i class="fas fa-info-circle"></i> Hôm nay bạn không được phân công chuyến đi nào.</p>
                </div>
            </c:if>

            <c:forEach var="trip" items="${trips}">
                <%-- Set class for card border based on status --%>
                <c:set var="statusClass">
                    <c:choose>
                        <c:when test="${trip.status == 'Chưa bắt đầu'}">status-chua-bat-dau</c:when>
                        <c:when test="${trip.status == 'Đang di chuyển'}">status-dang-di-chuyen</c:when>
                        <c:when test="${trip.status == 'Hoàn thành'}">status-hoan-thanh</c:when>
                        <c:otherwise>status-chua-bat-dau</c:otherwise>
                    </c:choose>
                </c:set>

                <div class="trip-card ${statusClass}">
                    <div class="card-body">
                        <div class="trip-details">
                            <h3>
                                <i class="fas fa-route"></i>
                                ${trip.route.routeName} 
                                <span style="font-weight: 400; color: #555;">(${trip.tripType == 'pickup' ? 'Chuyến đón' : 'Chuyến trả'})</span>
                            </h3>
                            <div class="info-row">
                                <i class="fas fa-bus"></i> 
                                <strong>Xe:</strong> ${trip.bus.licensePlate}
                            </div>
                            <div class="info-row">
                                <i class="fas fa-user-tie"></i> 
                                <strong>Lái xe:</strong> ${trip.driver.fullName}
                            </div>
                            <div class="info-row">
                                <i class="fas fa-spinner"></i>
                                <strong>Trạng thái:</strong> 
                                <c:set var="badgeClass">
                                    <c:choose>
                                        <c:when test="${trip.status == 'Chưa bắt đầu'}">badge-secondary</c:when>
                                        <c:when test="${trip.status == 'Đang di chuyển'}">badge-primary</c:when>
                                        <c:when test="${trip.status == 'Hoàn thành'}">badge-success</c:when>
                                        <c:otherwise>badge-secondary</c:otherwise>
                                    </c:choose>
                                </c:set>
                                <span class="status-badge ${badgeClass}">${trip.status}</span>
                            </div>
                        </div>
                        <div class="card-actions">
                            <a href="<c:url value='/monitor/trip/${trip.id}' />" class="btn btn-primary">
                                <i class="fas fa-play-circle"></i> Bắt đầu điểm danh
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </main>

    </body>
</html>