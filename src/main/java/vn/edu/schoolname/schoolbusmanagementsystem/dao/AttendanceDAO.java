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
    public void notifyAbsence(int tripId, int studentId, int stopId) {
        String checkSql = "SELECT id FROM attendance WHERE trip_id = ? AND student_id = ?";
        String updateSql = "UPDATE attendance SET status = 'notified_absence' WHERE id = ?";
        String insertSql = "INSERT INTO attendance (trip_id, student_id, stop_id, status) VALUES (?, ?, ?, 'notified_absence')";

        try (Connection conn = DBContext.getConnection()) {
            int attendanceId = -1;

            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, tripId);
                psCheck.setInt(2, studentId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        attendanceId = rs.getInt("id");
                    }
                }
            }

            if (attendanceId != -1) {
                try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                    psUpdate.setInt(1, attendanceId);
                    psUpdate.executeUpdate();
                }
            } else {
                try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                    psInsert.setInt(1, tripId);
                    psInsert.setInt(2, studentId);
                    psInsert.setInt(3, stopId); 
                    psInsert.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkIn(int tripId, int studentId, int stopId) {
        String checkSql = "SELECT id FROM attendance WHERE trip_id = ? AND student_id = ?";
        String updateSql = "UPDATE attendance SET status = 'boarded', check_in_time = ?, stop_id = ? WHERE id = ?";
        String insertSql = "INSERT INTO attendance (trip_id, student_id, stop_id, status, check_in_time) VALUES (?, ?, ?, 'Da len xe', ?)";

        try (Connection conn = DBContext.getConnection()) {
            int attendanceId = -1;

            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, tripId);
                psCheck.setInt(2, studentId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        attendanceId = rs.getInt("id");
                    }
                }
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());

            if (attendanceId != -1) {
                try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                    psUpdate.setTimestamp(1, now);
                    psUpdate.setInt(2, stopId);
                    psUpdate.setInt(3, attendanceId);
                    psUpdate.executeUpdate();
                }
            } else {
                try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                    psInsert.setInt(1, tripId);
                    psInsert.setInt(2, studentId);
                    psInsert.setInt(3, stopId);
                    psInsert.setTimestamp(4, now);
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkOut(int tripId, int studentId, int stopId) {
        String sql = "UPDATE attendance SET status = 'completed', check_out_time = ?, stop_id = ? WHERE trip_id = ? AND student_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, stopId); 
            ps.setInt(3, tripId);
            ps.setInt(4, studentId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
