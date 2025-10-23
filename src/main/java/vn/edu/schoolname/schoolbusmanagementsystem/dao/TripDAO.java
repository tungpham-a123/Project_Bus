package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Bus;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Route;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Trip;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TripDAO {

    public Trip getTodaysTripByStudentId(int studentId, String tripType) {
        String sql = "SELECT t.*, r.route_name, b.license_plate, driver.full_name as driver_name, monitor.full_name as monitor_name "
                + "FROM trips t "
                + "JOIN routes r ON t.route_id = r.id "
                + "JOIN buses b ON t.bus_id = b.id "
                + "JOIN users driver ON t.driver_id = driver.id "
                + "JOIN users monitor ON t.monitor_id = monitor.id "
                + "WHERE t.route_id = (SELECT route_id FROM registrations WHERE student_id = ? AND status = 'active') "
                + "AND t.trip_type = ? AND CONVERT(date, t.trip_date) = CONVERT(date, GETDATE())";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setString(2, tripType);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Trip trip = new Trip();
                    trip.setId(rs.getInt("id"));
                    trip.setTripDate(rs.getDate("trip_date"));
                    trip.setTripType(rs.getString("trip_type"));
                    trip.setStatus(rs.getString("status"));

                    Route route = new Route();
                    route.setRouteName(rs.getString("route_name"));
                    trip.setRoute(route);

                    Bus bus = new Bus();
                    bus.setLicensePlate(rs.getString("license_plate"));
                    trip.setBus(bus);

                    User driver = new User();
                    driver.setFullName(rs.getString("driver_name"));
                    trip.setDriver(driver);

                    User monitor = new User();
                    monitor.setFullName(rs.getString("monitor_name"));
                    trip.setMonitor(monitor);

                    return trip;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Trip> getTodaysTripsByMonitorId(int monitorId) {
        List<Trip> tripList = new ArrayList<>();
        String sql = "SELECT t.*, r.route_name, b.license_plate, driver.full_name as driver_name, monitor.full_name as monitor_name "
                + "FROM trips t "
                + "JOIN routes r ON t.route_id = r.id "
                + "JOIN buses b ON t.bus_id = b.id "
                + "JOIN users driver ON t.driver_id = driver.id "
                + "JOIN users monitor ON t.monitor_id = monitor.id "
                + "WHERE t.monitor_id = ? AND CONVERT(date, t.trip_date) = CONVERT(date, GETDATE()) "
                + "ORDER BY t.id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, monitorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Trip trip = new Trip();
                    trip.setId(rs.getInt("id"));
                    trip.setTripDate(rs.getDate("trip_date"));
                    trip.setTripType(rs.getString("trip_type"));
                    trip.setStatus(rs.getString("status"));
                    tripList.add(trip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tripList;
    }

}
