package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Attendance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AttendanceDAO {

    public Attendance getAttendanceStatus(int tripId, int studentId) {
        String sql = "SELECT * FROM attendance WHERE trip_id = ? AND student_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tripId);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setId(rs.getInt("id"));
                    attendance.setStatus(rs.getString("status"));
                    attendance.setCheckInTime(rs.getTimestamp("check_in_time"));
                    attendance.setCheckOutTime(rs.getTimestamp("check_out_time"));
                    return attendance;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

}
