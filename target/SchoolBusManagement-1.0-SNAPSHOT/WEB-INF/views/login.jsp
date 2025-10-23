<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng Nhập - Hệ Thống Xe Tuyến</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }

            body {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                background-color: #f0f2f5;
            }

            .login-container {
                width: 100%;
                max-width: 420px;
                padding: 40px 35px;
                background: #ffffff;
                border-radius: 16px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                text-align: center;
            }

            .login-header {
                margin-bottom: 25px;
                color: #0056b3;
                font-size: 28px;
                font-weight: 600;
            }

            .login-header .fa-bus-alt {
                color: #FFC107; /* School bus yellow */
                margin-right: 10px;
            }

            .role-selector {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-bottom: 25px;
            }

            .role-selector input[type="radio"] {
                display: none;
            }

            .role-selector label {
                padding: 8px 15px;
                border: 2px solid #ddd;
                border-radius: 50px;
                cursor: pointer;
                transition: all 0.3s ease;
                font-weight: 500;
            }

            .role-selector input[type="radio"]:checked + label {
                background-color: #FFC107;
                border-color: #FFC107;
                color: #333;
            }

            .input-group {
                position: relative;
                margin-bottom: 20px;
            }

            .input-group i {
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: #adadad;
            }

            .input-group input {
                width: 100%;
                height: 50px;
                padding: 0 20px 0 45px;
                border: 1px solid #ccc;
                border-radius: 8px;
                outline: none;
                font-size: 16px;
            }

            .input-group input:focus {
                border-color: #007bff;
            }

            .error {
                color: #d93025;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 20px;
                text-align: left;
            }

            .btn {
                width: 100%;
                padding: 14px;
                border: none;
                background: #007bff;
                color: white;
                border-radius: 8px;
                cursor: pointer;
                font-size: 18px;
                font-weight: 500;
                transition: background-color 0.3s ease;
            }

            .btn:hover {
                background: #0056b3;
            }

        </style>
    </head>
    <body>
        <div class="login-container">
            <h2 class="login-header"><i class="fas fa-bus-alt"></i>Hệ Thống Xe Tuyến</h2>

            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <form action="login" method="post">
                <div class="input-group">
                    <i class="fas fa-user"></i>
                    <input type="text" id="username" name="username" placeholder="Tên đăng nhập" required>
                </div>

                <div class="input-group">
                    <i class="fas fa-lock"></i>
                    <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
                </div>

                <button type="submit" class="btn">Đăng Nhập</button>
            </form>
        </div>
    </body>
</html>