-- Thêm dữ liệu cho bảng roles
INSERT INTO roles (role_name) VALUES
('admin'),
('parent'),
('driver'),
('monitor');
GO


-- Thêm dữ liệu cho bảng users
INSERT INTO users (username, password, full_name, phone, email, role_id) VALUES
-- Admin (role_id = 1)
('admin', '123', N'Quản Trị Viên', '0900000001', 'admin@school.edu.vn', 1),
-- Phụ huynh (role_id = 2)
('phuhuynh1', '123', N'Trần Văn An', '0900000002', 'an.tran@email.com', 2),
('phuhuynh2', '123', N'Nguyễn Thị Bình', '0900000003', 'binh.nguyen@email.com', 2),
-- Lái xe (role_id = 3)
('laixe1', '123', N'Lê Văn Cường', '0900000004', 'cuong.le@email.com', 3),
('laixe2', '123', N'Phạm Thị Dung', '0900000005', 'dung.pham@email.com', 3),
-- Quản lý xe (monitor, role_id = 4)
('quanly1', '123', N'Hoàng Văn Giang', '0900000006', 'giang.hoang@email.com', 4),
('quanly2', '123', N'Vũ Thị Hà', '0900000007', 'ha.vu@email.com', 4);
GO

-- Thêm dữ liệu cho bảng students
-- user 'phuhuynh1' có id = 2, 'phuhuynh2' có id = 3
INSERT INTO students (student_code, full_name, class_name, parent_id) VALUES
('HS001', N'Trần Minh Anh', N'Lớp 1A', 2),
('HS002', N'Nguyễn Hoàng Bách', N'Lớp 2B', 3),
('HS003', N'Nguyễn Tường Vy', N'Lớp 3C', 3);
GO


-- Thêm dữ liệu cho bảng buses
INSERT INTO buses (license_plate, capacity, status) VALUES
('29A-111.11', 20, 'active'),
('29B-222.22', 25, 'active'),
('29C-333.33', 20, 'maintenance');
GO


-- Thêm dữ liệu cho bảng routes (tuyến xe)
INSERT INTO routes (route_name, description) VALUES
(N'Tuyến số 1 - Cầu Giấy', N'Tuyến đi qua các khu vực Cầu Giấy, Mỹ Đình'),
(N'Tuyến số 2 - Hà Đông', N'Tuyến đi qua các khu vực Hà Đông, Thanh Xuân');
GO

-- Thêm dữ liệu cho bảng stops (điểm dừng)
INSERT INTO stops (stop_name, address) VALUES
(N'Điểm dừng Duy Tân', N'123 Duy Tân, Cầu Giấy'),
(N'Điểm dừng Mỹ Đình', N'Sân vận động Mỹ Đình, Nam Từ Liêm'),
(N'Điểm dừng Nguyễn Trãi', N'456 Nguyễn Trãi, Thanh Xuân'),
(N'Điểm dừng Quang Trung', N'789 Quang Trung, Hà Đông'),
(N'Trường học', N'Số 1 Đại Cồ Việt, Hai Bà Trưng'); -- Điểm dừng cuối cùng
GO

-- Liên kết các điểm dừng vào tuyến xe (route_stops)
-- Tuyến 1 (id=1) có 2 điểm đón và điểm trường học
INSERT INTO route_stops (route_id, stop_id, stop_order, estimated_pickup_time) VALUES
(1, 1, 1, '06:30:00'), -- Tuyến 1, điểm Duy Tân, thứ tự 1, giờ đón 6:30
(1, 2, 2, '06:45:00'), -- Tuyến 1, điểm Mỹ Đình, thứ tự 2, giờ đón 6:45
(1, 5, 3, '07:15:00'); -- Tuyến 1, điểm Trường học, thứ tự 3, giờ đến 7:15

-- Tuyến 2 (id=2) có 2 điểm đón và điểm trường học
INSERT INTO route_stops (route_id, stop_id, stop_order, estimated_pickup_time) VALUES
(2, 3, 1, '06:35:00'), -- Tuyến 2, điểm Nguyễn Trãi, thứ tự 1, giờ đón 6:35
(2, 4, 2, '06:50:00'), -- Tuyến 2, điểm Quang Trung, thứ tự 2, giờ đón 6:50
(2, 5, 3, '07:20:00'); -- Tuyến 2, điểm Trường học, thứ tự 3, giờ đến 7:20
GO



-- Đăng ký cho học sinh
-- student 'Trần Minh Anh' (id=1) đi Tuyến 1 (id=1), đón tại điểm Duy Tân (id=1)
INSERT INTO registrations (student_id, route_id, pickup_stop_id, status) VALUES
(1, 1, 1, 'active');

-- student 'Nguyễn Hoàng Bách' (id=2) đi Tuyến 2 (id=2), đón tại điểm Nguyễn Trãi (id=3)
INSERT INTO registrations (student_id, route_id, pickup_stop_id, status) VALUES
(2, 2, 3, 'active');

-- student 'Nguyễn Tường Vy' (id=3) đi Tuyến 2 (id=2), đón tại điểm Quang Trung (id=4)
INSERT INTO registrations (student_id, route_id, pickup_stop_id, status) VALUES
(3, 2, 4, 'active');
GO