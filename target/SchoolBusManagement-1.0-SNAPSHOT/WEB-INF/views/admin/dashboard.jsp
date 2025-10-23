<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
        <style>
            :root {
                --primary-color: #4a90e2;
                --secondary-color: #f5f7fa;
                --border-color: #e1e1e1;
                --text-primary: #333;
                --text-secondary: #777;
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

            /* Sidebar Navigation */
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

            /* Main Content Area */
            .main-content {
                flex-grow: 1;
            }

            .main-header {
                background-color: #ffffff;
                padding: 1rem 2rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid var(--border-color);
            }

            .search-bar {
                position: relative;
                width: 300px;
            }
            .search-bar input {
                width: 100%;
                padding: 0.6rem 1rem 0.6rem 2.5rem;
                border-radius: 20px;
                border: 1px solid var(--border-color);
                background-color: var(--secondary-color);
            }
            .search-bar i {
                position: absolute;
                left: 1rem;
                top: 50%;
                transform: translateY(-50%);
                color: var(--text-secondary);
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

            .content-header {
                margin-bottom: 1.5rem;
            }

            /* Dashboard Widgets */
            .dashboard-widgets {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 1.5rem;
            }

            .widget-card {
                background-color: #ffffff;
                padding: 1.5rem;
                border-radius: 12px;
                border-top: 4px solid;
                box-shadow: 0 4px 20px rgba(0,0,0,0.05);
            }

            .widget-card.blue {
                border-color: #3498db;
            }
            .widget-card.green {
                border-color: #2ecc71;
            }
            .widget-card.orange {
                border-color: #e67e22;
            }
            .widget-card.purple {
                border-color: #9b59b6;
            }
            .widget-card.teal {
                border-color: #1abc9c;
            }
            .widget-card.red {
                border-color: #e74c3c;
            }

            .widget-content {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
            }

            .widget-details .widget-value {
                font-size: 2.2rem;
                font-weight: 700;
                color: var(--text-primary);
            }

            .widget-details .widget-title {
                font-size: 1rem;
                color: var(--text-secondary);
            }

            .widget-icon {
                font-size: 1.8rem;
                color: var(--text-secondary);
                opacity: 0.5;
            }
        </style>
    </head>
    <body>
        <aside class="sidebar">
            <div class="sidebar-brand">
                <i class="fas fa-bus"></i> SchoolBus
            </div>
            <ul class="sidebar-nav">
                <li class="sidebar-nav-item active"><a href="#"><i class="fas fa-tachometer-alt nav-icon"></i> Tổng quan</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/users' />"><i class="fas fa-users-cog nav-icon"></i> Quản lý người dùng</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/students' />"><i class="fas fa-user-graduate nav-icon"></i> Quản lý học sinh</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/routes' />"><i class="fas fa-route nav-icon"></i> Quản lý tuyến xe</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/stops' />"><i class="fas fa-map-marker-alt nav-icon"></i> Quản lý điểm dừng</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/buses' />"><i class="fas fa-bus-alt nav-icon"></i> Quản lý xe buýt</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/trips' />"><i class="fas fa-calendar-alt nav-icon"></i> Quản lý chuyến đi</a></li>
                <li class="sidebar-nav-item"><a href="<c:url value='/admin/issues' />"><i class="fas fa-exclamation-triangle nav-icon"></i> Quản lý sự cố</a></li>
            </ul>
        </aside>

        <div class="main-content">
            <header class="main-header">
                <div class="search-bar">
                   
                </div>
                <div class="user-info">
                    <span>Chào mừng, <strong>${sessionScope.user.fullName}</strong></span>
                    <a href="<c:url value='/logout' />"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
                </div>
            </header>

            <main class="content-body">
                <div class="content-header">
                    <h2>Tổng quan hệ thống</h2>
                </div>

                <div class="dashboard-widgets">
                    <div class="widget-card blue">
                        <div class="widget-content">
                            <div class="widget-details">
                                <div class="widget-value">${userCount}</div>
                                <div class="widget-title">Người dùng</div>
                            </div>
                            <div class="widget-icon"><i class="fas fa-users"></i></div>
                        </div>
                    </div>
                    <div class="widget-card green">
                        <div class="widget-content">
                            <div class="widget-details">
                                <div class="widget-value">${studentCount}</div>
                                <div class="widget-title">Học sinh</div>
                            </div>
                            <div class="widget-icon"><i class="fas fa-user-graduate"></i></div>
                        </div>
                    </div>
                    <div class="widget-card orange">
                        <div class="widget-content">
                            <div class="widget-details">
                                <div class="widget-value">${routeCount}</div>
                                <div class="widget-title">Tuyến xe</div>
                            </div>
                            <div class="widget-icon"><i class="fas fa-route"></i></div>
                        </div>
                    </div>
                    <div class="widget-card purple">
                        <div class="widget-content">
                            <div class="widget-details">
                                <div class="widget-value">${busCount}</div>
                                <div class="widget-title">Xe buýt</div>
                            </div>
                            <div class="widget-icon"><i class="fas fa-bus-alt"></i></div>
                        </div>
                    </div>
                    <div class="widget-card teal">
                        <div class="widget-content">
                            <div class="widget-details">
                                <div class="widget-value">${todayTripCount}</div>
                                <div class="widget-title">Chuyến đi hôm nay</div>
                            </div>
                            <div class="widget-icon"><i class="fas fa-calendar-check"></i></div>
                        </div>
                    </div>
                    <div class="widget-card red">
                        <div class="widget-content">
                            <div class="widget-details">
                                <div class="widget-value">${pendingIssueCount}</div>
                                <div class="widget-title">Sự cố chờ xử lý</div>
                            </div>
                            <div class="widget-icon"><i class="fas fa-exclamation-triangle"></i></div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </body>
    
</html>