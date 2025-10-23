package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Registration;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Route;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Stop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO {

    public Registration getRegistrationByStudentId(int studentId) {
        String sql = "SELECT reg.*, r.route_name, s.stop_name, s.address "
                + "FROM registrations reg "
                + "JOIN routes r ON reg.route_id = r.id "
                + "JOIN stops s ON reg.pickup_stop_id = s.id "
                + "WHERE reg.student_id = ? AND reg.status = 'active'";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Registration reg = new Registration();
                    reg.setId(rs.getInt("id"));
                    reg.setStatus(rs.getString("status"));

                    Route route = new Route();
                    route.setRouteName(rs.getString("route_name"));
                    reg.setRoute(route);

                    Stop stop = new Stop();
                    stop.setStopName(rs.getString("stop_name"));
                    stop.setAddress(rs.getString("address"));
                    reg.setPickupStop(stop);

                    return reg;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}
