<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bảng điều khiển Lái xe</title>
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
                max-width: 1100px;
                margin: 2rem auto;
                padding: 0 1rem;
            }

            .page-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 1.5rem;
            }

            .page-title {
                font-size: 2rem;
                font-weight: 600;
                color: #343a40;
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

            .btn-primary {
                background-color: #007bff;
                color: white;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }

            /* Table Container Card */
            .card {
                background-color: #ffffff;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
                overflow: hidden;
            }

            .card-header {
                padding: 1rem 1.5rem;
                background-color: #f8f9fa;
                border-bottom: 1px solid #e9ecef;
                font-weight: 600;
                font-size: 1.1rem;
            }

            .card-body {
                padding: 0;
            }

            /* Modern Table Styles */
            .data-table {
                width: 100%;
                border-collapse: collapse;
            }

            .data-table th, .data-table td {
                padding: 1rem 1.5rem;
                text-align: left;
                border-bottom: 1px solid #e9ecef;
            }

            .data-table thead th {
                background-color: #f8f9fa;
                font-weight: 600;
                color: #495057;
            }

            .data-table tbody tr:last-child td {
                border-bottom: none;
            }

            .data-table tbody tr:hover {
                background-color: #f1f1f1;
            }

            /* Status Badge Styles */
            .status-badge {
                padding: 0.25em 0.6em;
                font-size: 0.85em;
                font-weight: 600;
                border-radius: 50px;
                color: #fff;
            }

            .badge-warning {
                background-color: #ffc107;
                color: #333;
            }
            .badge-primary {
                background-color: #007bff;
            }
            .badge-success {
                background-color: #28a745;
            }
            .badge-secondary {
                background-color: #6c757d;
            }

            .empty-state {
                padding: 3rem;
                text-align: center;
                color: #6c757d;
            }
        </style>
    </head>
    <body>
        <header class="navbar">
            <div class="navbar-brand">
                <i class="fas fa-steering-wheel" style="color: #6c757d;"></i> Driver Portal
            </div>
            <div>
                <span class="navbar-user">Xin chào, <strong>${sessionScope.user.fullName}</strong></span>
                <a href="<c:url value='/logout' />" class="btn-logout">
                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                </a>
            </div>
        </header>

        <main class="container">
            <div class="page-header">
                <h2 class="page-title">Bảng điều khiển</h2>
                <a href="<c:url value='/driver/report-issue' />" class="btn btn-primary">
                    <i class="fas fa-plus-circle"></i> Báo cáo sự cố mới
                </a>
            </div>

            <div class="card">
                <div class="card-header">
                    Lịch sử báo cáo
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty issues}">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Xe</th>
                                        <th>Ngày Báo Cáo</th>
                                        <th>Mô tả</th>
                                        <th>Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="issue" items="${issues}">
                                        <tr>
                                            <td>#${issue.id}</td>
                                            <td>${issue.bus.licensePlate}</td>
                                            <td><fmt:formatDate value="${issue.reportedDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                            <td>${issue.issueDescription}</td>
                                            <td>
                                                <c:set var="status" value="${issue.status}" />
                                                <c:set var="badgeClass">
                                                    <c:choose>
                                                        <c:when test="${status == 'Chờ xử lý'}">badge-warning</c:when>
                                                        <c:when test="${status == 'Đang xử lý'}">badge-primary</c:when>
                                                        <c:when test="${status == 'Đã giải quyết'}">badge-success</c:when>
                                                        <c:otherwise>badge-secondary</c:otherwise>
                                                    </c:choose>
                                                </c:set>
                                                <span class="status-badge ${badgeClass}">${status}</span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <p>Chưa có báo cáo nào được gửi.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
    </body>
</html>