package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Attendance;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Registration;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Stop;
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

    
}
