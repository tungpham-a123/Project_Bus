/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Route;
import vn.edu.schoolname.schoolbusmanagementsystem.model.RouteStop;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Stop;


public class RouteDAO {
    public List<Route> getAllRoutes() {
        List<Route> routeList = new ArrayList<>();
        String sql = "SELECT * FROM routes";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Route route = new Route();
                route.setId(rs.getInt("id"));
                route.setRouteName(rs.getString("route_name"));
                route.setDescription(rs.getString("description"));
                routeList.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeList;
    }

    public void addRoute(Route route) {
        String sql = "INSERT INTO routes (route_name, description) VALUES (?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, route.getRouteName());
            ps.setString(2, route.getDescription());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Route getRouteById(int id) {
        String sql = "SELECT * FROM routes WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Route route = new Route();
                    route.setId(rs.getInt("id"));
                    route.setRouteName(rs.getString("route_name"));
                    route.setDescription(rs.getString("description"));
                    return route;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRoute(Route route) {
        String sql = "UPDATE routes SET route_name = ?, description = ? WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, route.getRouteName());
            ps.setString(2, route.getDescription());
            ps.setInt(3, route.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoute(int id) {
        String deleteRouteStopsSql = "DELETE FROM route_stops WHERE route_id = ?";
        String deleteRouteSql = "DELETE FROM routes WHERE id = ?";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psRouteStops = conn.prepareStatement(deleteRouteStopsSql)) {
                psRouteStops.setInt(1, id);
                psRouteStops.executeUpdate();

                try (PreparedStatement psRoute = conn.prepareStatement(deleteRouteSql)) {
                    psRoute.setInt(1, id);
                    psRoute.executeUpdate();
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

    public List<RouteStop> getStopsByRouteId(int routeId) {
        List<RouteStop> routeStops = new ArrayList<>();
        String sql = "SELECT rs.stop_order, rs.estimated_pickup_time, s.id as stop_id, s.stop_name, s.address "
                + "FROM route_stops rs "
                + "JOIN stops s ON rs.stop_id = s.id "
                + "WHERE rs.route_id = ? "
                + "ORDER BY rs.stop_order ASC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, routeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stop stop = new Stop();
                    stop.setId(rs.getInt("stop_id"));
                    stop.setStopName(rs.getString("stop_name"));
                    stop.setAddress(rs.getString("address"));

                    RouteStop routeStop = new RouteStop();
                    routeStop.setStop(stop);
                    routeStop.setStopOrder(rs.getInt("stop_order"));
                    routeStop.setEstimatedPickupTime(rs.getTime("estimated_pickup_time"));

                    routeStops.add(routeStop);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeStops;
    }

    public void addStopToRoute(int routeId, int stopId, int stopOrder, Time estimatedTime) {
        String sql = "INSERT INTO route_stops (route_id, stop_id, stop_order, estimated_pickup_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            ps.setInt(2, stopId);
            ps.setInt(3, stopOrder);
            ps.setTime(4, estimatedTime);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStopFromRoute(int routeId, int stopId) {
        String sql = "DELETE FROM route_stops WHERE route_id = ? AND stop_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            ps.setInt(2, stopId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RouteStop getRouteStopDetails(int routeId, int stopId) {
        String sql = "SELECT rs.stop_order, rs.estimated_pickup_time, s.id as stop_id, s.stop_name, s.address "
                + "FROM route_stops rs "
                + "JOIN stops s ON rs.stop_id = s.id "
                + "WHERE rs.route_id = ? AND rs.stop_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            ps.setInt(2, stopId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Stop stop = new Stop();
                    stop.setId(rs.getInt("stop_id"));
                    stop.setStopName(rs.getString("stop_name"));
                    stop.setAddress(rs.getString("address"));

                    RouteStop routeStop = new RouteStop();
                    routeStop.setStop(stop);
                    routeStop.setStopOrder(rs.getInt("stop_order"));
                    routeStop.setEstimatedPickupTime(rs.getTime("estimated_pickup_time"));

                    return routeStop;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRouteStop(int routeId, int stopId, int stopOrder, Time estimatedTime) {
        String sql = "UPDATE route_stops SET stop_order = ?, estimated_pickup_time = ? WHERE route_id = ? AND stop_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, stopOrder);
            ps.setTime(2, estimatedTime);
            ps.setInt(3, routeId);
            ps.setInt(4, stopId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
