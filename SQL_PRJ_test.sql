CREATE DATABASE SchoolBusDB
use SchoolBusDB
-- Bảng Vai trò (Phân quyền)
CREATE TABLE roles (
    id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(20) UNIQUE NOT NULL -- 'parent', 'driver', 'monitor', 'admin'
);
GO

-- Bảng Người dùng
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Nên mã hóa mật khẩu trước khi lưu
    full_name NVARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
GO

-- Bảng Học sinh
CREATE TABLE students (
    id INT IDENTITY(1,1) PRIMARY KEY,
    student_code VARCHAR(20) UNIQUE NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    class_name NVARCHAR(50),
    parent_id INT NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES users(id)
);
GO

-- Bảng Xe buýt (Chỉ lưu thông tin về xe)
CREATE TABLE buses (
    id INT IDENTITY(1,1) PRIMARY KEY,
    license_plate VARCHAR(20) UNIQUE NOT NULL,
    capacity INT,
    status NVARCHAR(50) DEFAULT 'active' -- 'active', 'maintenance', 'out_of_service'
);
GO

-- Bảng Tuyến xe
CREATE TABLE routes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    route_name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255)
);
GO

-- Bảng Điểm dừng
CREATE TABLE stops (
    id INT IDENTITY(1,1) PRIMARY KEY,
    stop_name NVARCHAR(100) NOT NULL,
    address NVARCHAR(255)
);
GO

-- Bảng liên kết Tuyến - Điểm dừng (Bảng quan trọng để giải quyết vấn đề số 2 và 4)
CREATE TABLE route_stops (
    route_id INT NOT NULL,
    stop_id INT NOT NULL,
    stop_order INT NOT NULL, -- Thứ tự của điểm dừng trên tuyến (1, 2, 3...)
    estimated_pickup_time TIME, -- Giờ đón dự kiến (cho chuyến sáng)
    estimated_dropoff_time TIME, -- Giờ trả dự kiến (cho chuyến chiều)
    PRIMARY KEY (route_id, stop_id),
    FOREIGN KEY (route_id) REFERENCES routes(id),
    FOREIGN KEY (stop_id) REFERENCES stops(id)
);
GO

-- Bảng Đăng ký của Học sinh (Đơn giản hóa)
CREATE TABLE registrations (
    id INT IDENTITY(1,1) PRIMARY KEY,
    student_id INT NOT NULL,
    route_id INT NOT NULL,
    pickup_stop_id INT NOT NULL, -- Chỉ cần điểm đón, vì điểm trả thường là trường học
    registration_date DATE DEFAULT GETDATE(),
    status NVARCHAR(20) DEFAULT 'active', -- 'active', 'inactive'
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (route_id) REFERENCES routes(id),
    FOREIGN KEY (pickup_stop_id) REFERENCES stops(id)
);
GO

-- Bảng Chuyến đi hàng ngày (Linh hoạt hơn)
CREATE TABLE trips (
    id INT IDENTITY(1,1) PRIMARY KEY,
    trip_date DATE NOT NULL,
    trip_type NVARCHAR(10) NOT NULL, -- 'pickup' (đón), 'dropoff' (trả)
    status NVARCHAR(20) DEFAULT 'scheduled', -- 'scheduled', 'ongoing', 'completed', 'cancelled'
    actual_start_time DATETIME,
    actual_end_time DATETIME,
    -- Phân công linh hoạt cho từng chuyến đi
    route_id INT NOT NULL,
    bus_id INT NOT NULL,
    driver_id INT NOT NULL,
    monitor_id INT NOT NULL, -- Thêm quản lý xe cho chuyến đi
    FOREIGN KEY (route_id) REFERENCES routes(id),
    FOREIGN KEY (bus_id) REFERENCES buses(id),
    FOREIGN KEY (driver_id) REFERENCES users(id),
    FOREIGN KEY (monitor_id) REFERENCES users(id)
);
GO

-- Bảng Điểm danh (Chi tiết và mạnh mẽ hơn)
CREATE TABLE attendance (
    id INT IDENTITY(1,1) PRIMARY KEY,
    trip_id INT NOT NULL,
    student_id INT NOT NULL,
    stop_id INT NOT NULL, -- Ghi nhận học sinh lên/xuống tại điểm dừng nào
    check_in_time DATETIME, -- Giờ quản lý xe bấm nút điểm danh lên xe
    check_out_time DATETIME, -- Giờ quản lý xe bấm nút điểm danh xuống xe
    status NVARCHAR(20) NOT NULL, -- 'absent' (vắng), 'notified_absence' (báo nghỉ), 'boarded' (đã lên xe), 'completed' (đã tới trường/về nhà)
    FOREIGN KEY (trip_id) REFERENCES trips(id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (stop_id) REFERENCES stops(id)
);
GO

-- Bảng Báo cáo sự cố xe (Giữ nguyên, rất tốt)
CREATE TABLE bus_issues (
    id INT IDENTITY(1,1) PRIMARY KEY,
    bus_id INT NOT NULL,
    reported_by INT NOT NULL, -- User ID của tài xế
    issue_description NVARCHAR(MAX) NOT NULL,
    reported_date DATETIME DEFAULT GETDATE(),
    status NVARCHAR(20) DEFAULT 'reported', -- 'reported', 'in_progress', 'resolved'
    FOREIGN KEY (bus_id) REFERENCES buses(id),
    FOREIGN KEY (reported_by) REFERENCES users(id)
);
GO