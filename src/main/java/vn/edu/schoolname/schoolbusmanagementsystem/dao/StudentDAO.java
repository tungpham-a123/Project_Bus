package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Attendance;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Registration;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Stop;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Student;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

public class StudentDAO {

    public List<Student> getStudentsByParentId(int parentId) {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE parent_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullName(rs.getString("full_name"));
                    student.setClassName(rs.getString("class_name"));
                    studentList.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullName(rs.getString("full_name"));
                    student.setClassName(rs.getString("class_name"));
                    return student;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getStudentsForTrip(int routeId, int tripId) {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT s.*, reg.pickup_stop_id, att.status as attendance_status, att.check_in_time "
                + "FROM students s "
                + "JOIN registrations reg ON s.id = reg.student_id "
                + "LEFT JOIN attendance att ON s.id = att.student_id AND att.trip_id = ? "
                + "WHERE reg.route_id = ? AND reg.status = 'active'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            ps.setInt(2, routeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setFullName(rs.getString("full_name"));
                    student.setClassName(rs.getString("class_name"));

                    Attendance attendance = new Attendance();
                    attendance.setStatus(rs.getString("attendance_status"));
                    attendance.setCheckInTime(rs.getTimestamp("check_in_time"));

                    Registration reg = new Registration();
                    Stop pickupStop = new Stop();
                    pickupStop.setId(rs.getInt("pickup_stop_id"));
                    reg.setPickupStop(pickupStop);
                    reg.setAttendanceStatus(attendance);
                    student.setRegistration(reg);

                    studentList.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT s.*, p.full_name as parent_name FROM students s JOIN users p ON s.parent_id = p.id ORDER BY s.id DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStudentCode(rs.getString("student_code"));
                student.setFullName(rs.getString("full_name"));
                student.setClassName(rs.getString("class_name"));

                User parent = new User();
                parent.setId(rs.getInt("parent_id"));
                parent.setFullName(rs.getString("parent_name"));
                student.setParent(parent);

                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO students (student_code, full_name, class_name, parent_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getStudentCode());
            ps.setString(2, student.getFullName());
            ps.setString(3, student.getClassName());
            ps.setInt(4, student.getParent().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        String sql = "UPDATE students SET student_code = ?, full_name = ?, class_name = ?, parent_id = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getStudentCode());
            ps.setString(2, student.getFullName());
            ps.setString(3, student.getClassName());
            ps.setInt(4, student.getParent().getId());
            ps.setInt(5, student.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        String deleteAttendanceSql = "DELETE FROM attendance WHERE student_id = ?";
        String deleteRegistrationsSql = "DELETE FROM registrations WHERE student_id = ?";
        String deleteStudentSql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement ps = conn.prepareStatement(deleteAttendanceSql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteRegistrationsSql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteStudentSql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
