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
    public List<Trip> getAllTrips() {
        List<Trip> tripList = new ArrayList<>();
        String sql = "SELECT t.*, r.route_name, b.license_plate, driver.full_name as driver_name, monitor.full_name as monitor_name "
                + "FROM trips t "
                + "JOIN routes r ON t.route_id = r.id "
                + "JOIN buses b ON t.bus_id = b.id "
                + "JOIN users driver ON t.driver_id = driver.id "
                + "JOIN users monitor ON t.monitor_id = monitor.id "
                + "ORDER BY t.trip_date DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Trip trip = new Trip();
                trip.setId(rs.getInt("id"));
                trip.setTripDate(rs.getDate("trip_date"));
                trip.setTripType(rs.getString("trip_type"));
                trip.setStatus(rs.getString("status"));
                trip.setActualStartTime(rs.getTimestamp("actual_start_time"));
                trip.setActualEndTime(rs.getTimestamp("actual_end_time"));

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

                tripList.add(trip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tripList;
    }

    public void addTrip(Trip trip) {
        String sql = "INSERT INTO trips (trip_date, trip_type, status, route_id, bus_id, driver_id, monitor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new Date(trip.getTripDate().getTime()));
            ps.setString(2, trip.getTripType());
            ps.setString(3, "scheduled"); // Mặc định trạng thái
            ps.setInt(4, trip.getRoute().getId());
            ps.setInt(5, trip.getBus().getId());
            ps.setInt(6, trip.getDriver().getId());
            ps.setInt(7, trip.getMonitor().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Trip getTripById(int id) {
        String sql = "SELECT * FROM trips WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Trip trip = new Trip();
                    trip.setId(rs.getInt("id"));
                    trip.setTripDate(rs.getDate("trip_date"));
                    trip.setTripType(rs.getString("trip_type"));
                    trip.setStatus(rs.getString("status"));

                    Route route = new Route();
                    route.setId(rs.getInt("route_id"));
                    trip.setRoute(route);

                    Bus bus = new Bus();
                    bus.setId(rs.getInt("bus_id"));
                    trip.setBus(bus);

                    User driver = new User();
                    driver.setId(rs.getInt("driver_id"));
                    trip.setDriver(driver);

                    User monitor = new User();
                    monitor.setId(rs.getInt("monitor_id"));
                    trip.setMonitor(monitor);

                    return trip;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTrip(Trip trip) {
        String sql = "UPDATE trips SET trip_date = ?, trip_type = ?, route_id = ?, bus_id = ?, driver_id = ?, monitor_id = ? WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(trip.getTripDate().getTime()));
            ps.setString(2, trip.getTripType());
            ps.setInt(3, trip.getRoute().getId());
            ps.setInt(4, trip.getBus().getId());
            ps.setInt(5, trip.getDriver().getId());
            ps.setInt(6, trip.getMonitor().getId());
            ps.setInt(7, trip.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTrip(int id) {
        String deleteAttendanceSql = "DELETE FROM attendance WHERE trip_id = ?";

        String deleteTripSql = "DELETE FROM trips WHERE id = ?";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            try {
                try (PreparedStatement ps = conn.prepareStatement(deleteAttendanceSql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteTripSql)) {
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
