<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Phụ Huynh</title>
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

        .container {
            max-width: 900px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .page-title {
            margin-bottom: 1.5rem;
            font-size: 2rem;
            font-weight: 600;
            color: #343a40;
        }

        .student-card {
            background-color: #ffffff;
            border-radius: 12px;
            margin-bottom: 2rem;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            overflow: hidden;
            border-left: 6px solid;
            transition: all 0.3s ease;
        }
        
        .student-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }

        .registered { border-color: #28a745; }
        .unregistered { border-color: #ffc107; }

        .card-header {
            padding: 1rem 1.5rem;
            background-color: #f8f9fa;
            border-bottom: 1px solid #e9ecef;
        }

        .card-header h3 {
            margin: 0;
            font-size: 1.25rem;
            font-weight: 600;
        }

        .card-body {
            padding: 1.5rem;
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .info-row {
            display: flex;
            align-items: center;
            gap: 10px;
            color: #555;
        }
        
        .info-row i {
            color: #007bff;
            width: 20px;
            text-align: center;
        }

        .trip-info {
            background-color: #f8f9fa;
            border: 1px solid #e9ecef;
            padding: 1rem;
            border-radius: 8px;
            margin-top: 1rem;
        }
        
        .trip-info h4 {
            margin-top: 0;
            margin-bottom: 1rem;
            font-weight: 600;
        }

        .status-badge {
            padding: 0.25em 0.6em;
            font-size: 0.85em;
            font-weight: 600;
            border-radius: 50px;
            color: #fff;
        }
        
        .status-present { background-color: #28a745; }
        .status-absent { background-color: #dc3545; }
        .status-pending { background-color: #6c757d; }
        .status-checked-out { background-color: #17a2b8; }

        .btn {
            padding: 0.6rem 1.2rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-weight: 500;
            font-size: 0.9rem;
            transition: all 0.3s;
            display: inline-block;
        }
        
        .btn-primary {
            background-color: #007bff;
            color: white;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
        }

        .btn-danger:hover {
            background-color: #c82333;
        }
        
        .action-area {
            margin-top: 1rem;
            padding-top: 1rem;
            border-top: 1px solid #e9ecef;
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
        <h2 class="page-title">Bảng Theo Dõi</h2>

        <c:forEach var="student" items="${students}">
            <div class="student-card ${not empty student.registration ? 'registered' : 'unregistered'}">
                <div class="card-header">
                    <h3><i class="fas fa-child"></i> ${student.fullName} - Lớp: ${student.className}</h3>
                </div>
                
                <div class="card-body">
                    <c:if test="${not empty student.registration}">
                        <div class="info-row">
                            <i class="fas fa-route"></i> 
                            <strong>Tuyến:</strong> ${student.registration.route.routeName}
                        </div>
                        <div class="info-row">
                            <i class="fas fa-map-marker-alt"></i>
                            <strong>Điểm đón:</strong> ${student.registration.pickupStop.stopName}
                        </div>

                        <div class="trip-info">
                            <h4><i class="fas fa-info-circle"></i> Thông tin chuyến đi hôm nay</h4>
                            <c:choose>
                                <c:when test="${not empty student.registration.todaysTrip}">
                                    <div class="info-row">
                                        <i class="fas fa-bus"></i> 
                                        <strong>Xe:</strong> ${student.registration.todaysTrip.bus.licensePlate}
                                    </div>
                                    <div class="info-row">
                                        <i class="fas fa-user-tie"></i> 
                                        <strong>Lái xe:</strong> ${student.registration.todaysTrip.driver.fullName}
                                    </div>
                                    <div class="info-row">
                                        <i class="fas fa-spinner"></i>
                                        <strong>Trạng thái chuyến:</strong> ${student.registration.todaysTrip.status}
                                    </div>
                                    <div class="info-row">
                                        <i class="fas fa-check-circle"></i> 
                                        <strong>Trạng thái của bé:</strong>
                                        <c:choose>
                                            <c:when test="${not empty student.registration.attendanceStatus}">
                                                <c:set var="status" value="${student.registration.attendanceStatus.status}"/>
                                                <span class="status-badge 
                                                    ${status == 'Đã lên xe' ? 'status-present' : ''}
                                                    ${status == 'Đã xuống xe' ? 'status-checked-out' : ''}
                                                    ${status == 'Vắng' ? 'status-absent' : ''}">
                                                    ${status}
                                                </span>
                                                <c:if test="${not empty student.registration.attendanceStatus.checkInTime}">
                                                    (Lúc <fmt:formatDate value="${student.registration.attendanceStatus.checkInTime}" pattern="HH:mm" />)
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-pending">Chưa điểm danh</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p>Chưa có thông tin chuyến đi cho hôm nay.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                        <div class="action-area">
                            <c:if test="${empty student.registration.attendanceStatus or student.registration.attendanceStatus.status == 'Vắng'}">
                                <form action="<c:url value='/parent/notify-absence' />" method="post" style="display: inline;">
                                    <input type="hidden" name="studentId" value="${student.id}">
                                    <input type="hidden" name="tripId" value="${student.registration.todaysTrip.id}">
                                    <input type="hidden" name="stopId" value="${student.registration.pickupStop.id}">
                                    <button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn báo nghỉ cho bé ${student.fullName} hôm nay không?');">
                                        <i class="fas fa-calendar-times"></i> Báo nghỉ hôm nay
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </c:if>

                    <c:if test="${empty student.registration}">
                        <p><strong>Trạng thái:</strong> Bé chưa được đăng ký xe tuyến.</p>
                        <div class="action-area">
                            <a href="<c:url value='/parent/register/step1/${student.id}' />" class="btn btn-primary">
                                <i class="fas fa-plus-circle"></i> Đăng ký xe tuyến cho bé
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </main>

</body>
</html>