<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Báo Cáo Sự Cố Xe</title>
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
                max-width: 700px;
                margin: 2rem auto;
                padding: 0 1rem;
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
                margin-bottom: 2rem;
                color: #343a40;
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

            .form-group select, .form-group textarea {
                width: 100%;
                padding: 0.75rem 1rem;
                border: 1px solid #ced4da;
                border-radius: 8px;
                font-size: 1rem;
                font-family: 'Poppins', sans-serif;
                background-color: #fff;
                transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
            }

            .form-group textarea {
                min-height: 120px;
                resize: vertical;
            }

            .form-group select:focus, .form-group textarea:focus {
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
                display: inline-flex;
                align-items: center;
                gap: 0.5rem;
            }

            .btn-danger {
                background-color: #dc3545;
                color: white;
            }
            .btn-danger:hover {
                background-color: #c82333;
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
            <div class="form-card">
                <h1><i class="fas fa-exclamation-triangle" style="color: #dc3545;"></i> Báo Cáo Sự Cố Mới</h1>

                <form action="<c:url value='/driver/report-issue'/>" method="post">
                    <div class="form-group">
                        <label for="busId"><i class="fas fa-bus"></i> Chọn xe gặp sự cố</label>
                        <select id="busId" name="busId" required>
                            <option value="" disabled selected>-- Vui lòng chọn xe --</option>
                            <c:forEach var="bus" items="${buses}">
                                <option value="${bus.id}">${bus.licensePlate}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="issueDescription"><i class="fas fa-comment-dots"></i> Mô tả chi tiết sự cố</label>
                        <textarea id="issueDescription" name="issueDescription" rows="5" placeholder="Ví dụ: Lốp sau bên trái bị xì hơi, đèn pha không hoạt động..." required></textarea>
                    </div>

                    <div class="form-actions">
                        <a href="<c:url value='/driver/dashboard'/>" class="btn btn-secondary">
                            <i class="fas fa-times"></i> Hủy
                        </a>
                        <button type="submit" class="btn btn-danger">
                            <i class="fas fa-paper-plane"></i> Gửi Báo Cáo
                        </button>
                    </div>
                </form>
            </div>
        </main>

    </body>
</html>