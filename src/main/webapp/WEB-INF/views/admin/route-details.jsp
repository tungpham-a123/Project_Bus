<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi Tiết Tuyến Xe - Admin</title>
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
                --danger-color: #e74c3c;
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

            /* Page Header & Breadcrumbs */
            .page-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 1.5rem;
            }
            .page-title {
                font-size: 1.8rem;
                font-weight: 600;
                color: var(--text-primary);
            }
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

            /* Card and Table Styles */
            .card {
                background-color: #ffffff;
                border-radius: 12px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.05);
                overflow: hidden;
                margin-bottom: 2rem;
            }
            .card-header {
                padding: 1rem 1.5rem;
                background-color: #f8f9fa;
                border-bottom: 1px solid var(--border-color);
                font-weight: 600;
                font-size: 1.1rem;
            }
            .card-body {
                padding: 1.5rem;
            }
            .card-body p {
                margin-top: 0;
            }

            .data-table {
                width: 100%;
                border-collapse: collapse;
            }
            .data-table th, .data-table td {
                padding: 1rem 1.5rem;
                text-align: left;
                border-bottom: 1px solid var(--border-color);
            }
            .data-table thead th {
                background-color: #f8f9fa;
                font-weight: 600;
                color: #495057;
                text-transform: uppercase;
                font-size: 0.85rem;
            }
            .data-table tbody tr:last-child td {
                border-bottom: none;
            }
            .data-table tbody tr:hover {
                background-color: #f5f7fa;
            }

            .actions a {
                text-decoration: none;
                margin-right: 0.5rem;
                font-size: 1.2rem;
                transition: color 0.3s;
            }
            .action-edit {
                color: var(--primary-color);
            }
            .action-edit:hover {
                color: #2980b9;
            }
            .action-delete {
                color: var(--danger-color);
            }
            .action-delete:hover {
                color: #c0392b;
            }

            .btn {
                padding: 0.6rem 1.2rem;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                font-weight: 500;
                font-size: 0.9rem;
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
                    Chi tiết tuyến
                </div>

                <div class="page-header">
                    <h2 class="page-title">Chi tiết Tuyến: ${route.routeName}</h2>
                    <a href="<c:url value='/admin/routes/${route.id}/add-stop' />" class="btn btn-success">
                        <i class="fas fa-plus-circle"></i> Thêm điểm dừng
                    </a>
                </div>

                <div class="card">
                    <div class="card-header">
                        Thông tin chung
                    </div>
                    <div class="card-body">
                        <p><strong>Mô tả:</strong> ${route.description != null && !route.description.isEmpty() ? route.description : 'Không có mô tả.'}</p>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        Danh sách điểm dừng trên tuyến
                    </div>
                    <div class="card-body" style="padding: 0;">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>Thứ tự</th>
                                    <th>Tên điểm dừng</th>
                                    <th>Địa chỉ</th>
                                    <th>Giờ đón dự kiến</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="routeStop" items="${stops}">
                                    <tr>
                                        <td><strong>${routeStop.stopOrder}</strong></td>
                                        <td>${routeStop.stop.stopName}</td>
                                        <td>${routeStop.stop.address}</td>
                                        <td>${routeStop.estimatedPickupTime}</td>
                                        <td class="actions">
                                            <a href="<c:url value='/admin/routes/${route.id}/edit-stop/${routeStop.stop.id}' />" class="action-edit" title="Sửa">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="<c:url value='/admin/routes/${route.id}/remove-stop/${routeStop.stop.id}' />" class="action-delete" title="Xóa"
                                               onclick="return confirm('Bạn có chắc chắn muốn gỡ điểm dừng [${routeStop.stop.stopName}] khỏi tuyến này không?');">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty stops}">
                                    <tr>
                                        <td colspan="5" style="text-align: center; padding: 2rem;">Chưa có điểm dừng nào được thêm vào tuyến này.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>
        </div>
    </body>
</html>