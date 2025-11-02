<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thêm Điểm Dừng - Admin</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
        <style>
            :root {
                --primary-color: #4a90e2;
                --secondary-color: #f5f7fa;
                --border-color: #e1e1e1;
                --text-primary: #333;
                --text-secondary: #777;
                --success-color: #2ecc71;
            }

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }

            body {
                display: flex;
                background-color: var(--secondary-color);
            }

            /* Sidebar Styles */
            .sidebar {
                width: 260px;
                background-color: #ffffff;
                min-height: 100vh;
                border-right: 1px solid var(--border-color);
                display: flex;
                flex-direction: column;
            }
            .sidebar-brand {
                padding: 1.5rem;
                text-align: center;
                font-size: 1.8rem;
                font-weight: 700;
                color: var(--primary-color);
                border-bottom: 1px solid var(--border-color);
            }
            .sidebar-brand .fa-bus {
                color: #FFC107;
            }
            .sidebar-nav {
                list-style: none;
                flex-grow: 1;
                padding-top: 1rem;
            }
            .sidebar-nav-item a {
                display: flex;
                align-items: center;
                gap: 1rem;
                padding: 1rem 1.5rem;
                margin: 0.25rem 0.5rem;
                color: var(--text-secondary);
                text-decoration: none;
                transition: all 0.3s ease;
                border-radius: 8px;
                font-weight: 500;
            }
            .sidebar-nav-item a:hover {
                background-color: var(--secondary-color);
                color: var(--primary-color);
            }
            .sidebar-nav-item.active a {
                background-color: var(--primary-color);
                color: white;
            }
            .sidebar-nav-item a .nav-icon {
                width: 20px;
            }

            /* Main Content Styles */
            .main-content {
                flex-grow: 1;
            }
            .main-header {
                background-color: #ffffff;
                padding: 1rem 2rem;
                display: flex;
                justify-content: flex-end;
                align-items: center;
                border-bottom: 1px solid var(--border-color);
            }
            .user-info a {
                text-decoration: none;
                color: #e74c3c;
                font-weight: 500;
                margin-left: 1rem;
            }
            .user-info strong {
                color: var(--text-primary);
            }
            .content-body {
                padding: 2rem;
            }

            /* Breadcrumbs */
            .breadcrumbs {
                margin-bottom: 1.5rem;
                color: var(--text-secondary);
                font-size: 0.9rem;
            }
            .breadcrumbs a {
                text-decoration: none;
                color: var(--primary-color);
            }
            .breadcrumbs span {
                margin: 0 0.5rem;
            }

            /* Card and Form Styles */
            .card {
                background-color: #ffffff;
                padding: 2rem 2.5rem;
                border-radius: 12px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.05);
            }
            .card-header {
                margin-bottom: 2rem;
            }
            .card-header h2 {
                font-size: 1.5rem;
                color: var(--text-primary);
                margin-bottom: 0.25rem;
            }
            .card-header p {
                color: var(--text-secondary);
            }

            .form-group {
                margin-bottom: 1.5rem;
            }
            .form-group label {
                display: block;
                margin-bottom: 0.5rem;
                font-weight: 500;
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }
            .form-group input, .form-group select {
                width: 100%;
                padding: 0.75rem 1rem;
                border: 1px solid var(--border-color);
                border-radius: 8px;
                font-size: 1rem;
                font-family: 'Poppins', sans-serif;
            }
            .form-group input:focus, .form-group select:focus {
                outline: none;
                border-color: var(--primary-color);
                box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
            }
            .form-actions {
                margin-top: 2rem;
                display: flex;
                justify-content: flex-end;
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
                display: inline-flex;
                align-items: center;
                gap: 0.5rem;
            }
            .btn-success {
                background-color: var(--success-color);
                color: white;
            }
            .btn-success:hover {
                background-color: #28b463;
            }
            .btn-secondary {
                background-color: #ccc;
                color: var(--text-primary);
            }
            .btn-secondary:hover {
                background-color: #bbb;
            }
        </style>
    </head>
    <body>
        <aside class="sidebar">
            <div class="sidebar-brand">
                <i class="fas fa-bus"></i> SchoolBus
            </div>
            <ul class="sidebar-nav">
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/dashboard'/>"><i class="fas fa-tachometer-alt nav-icon"></i> Tổng quan</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/users' />"><i class="fas fa-users-cog nav-icon"></i> Quản lý người dùng</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/students' />"><i class="fas fa-user-graduate nav-icon"></i> Quản lý học sinh</a></li>
                <li class="sidebar-nav-item active"><a href="<c:url value='/admin/routes' />"><i class="fas fa-route nav-icon"></i> Quản lý tuyến xe</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/stops' />"><i class="fas fa-map-marker-alt nav-icon"></i> Quản lý điểm dừng</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/buses' />"><i class="fas fa-bus-alt nav-icon"></i> Quản lý xe buýt</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/trips' />"><i class="fas fa-calendar-alt nav-icon"></i> Quản lý chuyến đi</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/issues' />"><i class="fas fa-exclamation-triangle nav-icon"></i> Quản lý sự cố</a></li>
            </ul>
        </aside>

        <div class="main-content">
            <header class="main-header">
                <div class="user-info">
                    <span>Chào mừng, <strong>${sessionScope.user.fullName}</strong></span>
                    <a href="<c:url value='/logout' />"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                </div>
            </header>

            <main class="content-body">
                <div class="breadcrumbs">
                    <a href="<c:url value='/admin/dashboard'/>">Dashboard</a>
                    <span>&gt;</span>
                    <a href="<c:url value='/admin/routes'/>">Quản lý tuyến xe</a>
                    <span>&gt;</span>
                    <a href="<c:url value='/admin/routes/details/${route.id}'/>">Chi tiết tuyến</a>
                    <span>&gt;</span>
                    Thêm điểm dừng
                </div>

                <div class="card">
                    <div class="card-header">
                        <h2>Thêm điểm dừng vào tuyến</h2>
                        <p>Tuyến: <strong>${route.routeName}</strong></p>
                    </div>

                    <form action="<c:url value='/admin/routes/add-stop'/>" method="post">
                        <input type="hidden" name="routeId" value="${route.id}">

                        <div class="form-group">
                            <label for="stopId"><i class="fas fa-map-pin"></i> Chọn điểm dừng có sẵn</label>
                            <select id="stopId" name="stopId" required>
                                <option value="" disabled selected>-- Vui lòng chọn một điểm dừng --</option>
                                <c:forEach var="stop" items="${availableStops}">
                                    <option value="${stop.id}">${stop.stopName} (${stop.address})</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="stopOrder"><i class="fas fa-sort-numeric-up"></i> Thứ tự trên tuyến</label>
                            <input type="number" id="stopOrder" name="stopOrder" placeholder="Ví dụ: 1, 2, 3..." required min="1">
                        </div>

                        <div class="form-group">
                            <label for="estimatedPickupTime"><i class="fas fa-clock"></i> Giờ đón dự kiến</label>
                            <input type="time" id="estimatedPickupTime" name="estimatedPickupTime" required>
                        </div>

                        <div class="form-actions">
                            <a href="<c:url value='/admin/routes/details/${route.id}'/>" class="btn btn-secondary">
                                <i class="fas fa-times"></i> Hủy
                            </a>
                            <button type="submit" class="btn btn-success">
                                <i class="fas fa-plus-circle"></i> Thêm vào tuyến
                            </button>
                        </div>
                    </form>
                </div>
            </main>
        </div>
    </body>
</html>