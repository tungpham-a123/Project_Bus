<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Điểm Danh Chuyến Đi</title>
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

            .container {
                max-width: 1000px;
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

            .btn-secondary {
                background-color: #6c757d;
                color: white;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
            }
            .btn-success {
                background-color: #28a745;
                color: white;
            }
            .btn-success:hover {
                background-color: #218838;
            }
            .btn-danger {
                background-color: #dc3545;
                color: white;
            }
            .btn-danger:hover {
                background-color: #c82333;
            }
            .btn-checkin {
                background-color: #007bff;
                color: white;
            }
            .btn-checkout {
                background-color: #ffc107;
                color: #333;
            }

            .trip-info-card {
                background-color: #ffffff;
                padding: 1.5rem;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
                margin-bottom: 2rem;
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
                gap: 1rem;
            }

            .info-details .info-row {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-bottom: 0.5rem;
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
            .badge-danger {
                background-color: #dc3545;
            }
            .badge-warning {
                background-color: #ffc107;
                color: #333;
            }

            .student-list-container {
                background-color: #ffffff;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
                overflow: hidden;
            }

            .student-list-header, .student-row {
                display: grid;
                grid-template-columns: 1fr 1fr 1fr;
                align-items: center;
                padding: 1rem 1.5rem;
            }

            .student-list-header {
                background-color: #f8f9fa;
                font-weight: 600;
                color: #495057;
                border-bottom: 2px solid #e9ecef;
            }

            .student-row {
                border-bottom: 1px solid #e9ecef;
            }

            .student-row:last-child {
                border-bottom: none;
            }

            .student-details {
                display: flex;
                flex-direction: column;
            }

            .student-details .name {
                font-weight: 500;
            }
            .student-details .code {
                font-size: 0.85rem;
                color: #6c757d;
            }

            .student-row.checked-in, .student-row.checked-out, .student-row.notified-absence {
                opacity: 0.6;
                background-color: #f8f9fa;
            }

            .student-actions form {
                display: inline;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .student-list-header {
                    display: none;
                }
                .student-row {
                    grid-template-columns: 1fr;
                    gap: 1rem;
                }
                .student-row > div::before {
                    content: attr(data-label);
                    font-weight: 600;
                    display: block;
                    margin-bottom: 0.25rem;
                    color: #333;
                }
            }
        </style>
    </head>
    <body>
        <main class="container">
            <div class="page-header">
                <h1 class="page-title">Điểm Danh Chuyến Đi</h1>
                <a href="<c:url value='/monitor/dashboard'/>" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
            </div>

            <div class="trip-info-card">
                <div class="info-details">
                    <h3>${trip.route.routeName}</h3>
                    <div class="info-row">
                        <strong>Ngày:</strong> <fmt:formatDate value="${trip.tripDate}" pattern="dd/MM/yyyy" />
                    </div>
                    <div class="info-row">
                        <strong>Loại:</strong> ${trip.tripType == 'pickup' ? 'Đón học sinh' : 'Trả học sinh'}
                    </div>
                    <div class="info-row">
                        <strong>Trạng thái:</strong>
                        <c:set var="badgeClass">
                            <c:choose>
                                <c:when test="${trip.status == 'scheduled'}">badge-secondary</c:when>
                                <c:when test="${trip.status == 'ongoing'}">badge-primary</c:when>
                                <c:when test="${trip.status == 'completed'}">badge-success</c:when>
                            </c:choose>
                        </c:set>
                        <span class="status-badge ${badgeClass}">${trip.status}</span>
                    </div>
                </div>
                <div class="info-actions">
                    <c:if test="${trip.status == 'scheduled'}">
                        <form action="<c:url value='/monitor/trip/start' />" method="post">
                            <input type="hidden" name="tripId" value="${trip.id}">
                            <button type="submit" class="btn btn-success"><i class="fas fa-play"></i> Bắt Đầu Chuyến Đi</button>
                        </form>
                    </c:if>
                    <c:if test="${trip.status == 'ongoing'}">
                        <form action="<c:url value='/monitor/trip/end' />" method="post">
                            <input type="hidden" name="tripId" value="${trip.id}">
                            <button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn kết thúc chuyến đi này không?');">
                                <i class="fas fa-stop-circle"></i> Kết Thúc Chuyến Đi
                            </button>
                        </form>
                    </c:if>
                </div>
            </div>

            <div class="student-list-container">
                <div class="student-list-header">
                    <div>Học sinh</div>
                    <div>Trạng thái</div>
                    <div>Hành động</div>
                </div>
                <c:forEach var="student" items="${students}">

                    <c:set var="attendance" value="${student.registration.attendanceStatus}" />
                    <c:set var="status" value="${attendance.status}" />

                    <c:set var="rowClass" value="" />
                    <c:if test="${status == 'boarded' or status == 'completed' or status == 'notified_absence'}">
                        <c:set var="rowClass" value="checked-in" />
                    </c:if>

                    <div class="student-row ${rowClass}">
                        <div class="student-details" data-label="Học sinh">
                            <span class="name">${student.fullName}</span>
                            <span class="code">Mã HS: ${student.studentCode}</span>
                        </div>

                        <div class="student-status" data-label="Trạng thái">
                            <c:choose>
                                <c:when test="${status == 'boarded'}">
                                    <span class="status-badge badge-success">Đã lên xe (lúc <fmt:formatDate value="${attendance.checkInTime}" pattern="HH:mm"/>)</span>
                                </c:when>
                                <c:when test="${status == 'notified_absence'}">
                                    <span class="status-badge badge-danger">PH báo nghỉ</span>
                                </c:when>
                                <c:when test="${status == 'completed'}">
                                    <span class="status-badge badge-primary">Đã xuống xe (lúc <fmt:formatDate value="${attendance.checkOutTime}" pattern="HH:mm"/>)</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-badge badge-secondary">Chưa có mặt</span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="student-actions" data-label="Hành động">
                            <c:if test="${trip.status == 'ongoing'}">

                                <c:if test="${empty status or status == 'absent' or status == 'Chưa có mặt'}">
                                    <form action="<c:url value='/monitor/trip/check-in' />" method="post">
                                        <input type="hidden" name="tripId" value="${trip.id}">
                                        <input type="hidden" name="studentId" value="${student.id}">
                                        <input type="hidden" name="stopId" value="${student.registration.pickupStop.id}">
                                        <button type="submit" class="btn btn-checkin"><i class="fas fa-user-check"></i> Check-in</button>
                                    </form>
                                </c:if>

                                <c:if test="${status == 'boarded'}">
                                    <form action="<c:url value='/monitor/trip/check-out' />" method="post">
                                        <input type="hidden" name="tripId" value="${trip.id}">
                                        <input type="hidden" name="studentId" value="${student.id}">
                                        <input type="hidden" name="stopId" value="${student.registration.pickupStop.id}"> 
                                        <button type="submit" class="btn btn-checkout"><i class="fas fa-user-minus"></i> Check-out</button>
                                    </form>
                                </c:if>

                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </body>
</html>