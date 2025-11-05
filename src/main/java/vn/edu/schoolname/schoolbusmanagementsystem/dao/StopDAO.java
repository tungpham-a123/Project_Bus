/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Stop;

public class StopDAO {

    public List<Stop> getAvailableStopsForRoute(int routeId) {
        List<Stop> stopList = new ArrayList<>();
        String sql = "SELECT * FROM stops WHERE id NOT IN (SELECT stop_id FROM route_stops WHERE route_id = ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, routeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stop stop = new Stop();
                    stop.setId(rs.getInt("id"));
                    stop.setStopName(rs.getString("stop_name"));
                    stop.setAddress(rs.getString("address"));
                    stopList.add(stop);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopList;
    }

    public List<Stop> getAllStops() {
        List<Stop> stopList = new ArrayList<>();
        String sql = "SELECT * FROM stops ORDER BY id DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Stop stop = new Stop();
                stop.setId(rs.getInt("id"));
                stop.setStopName(rs.getString("stop_name"));
                stop.setAddress(rs.getString("address"));
                stopList.add(stop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopList;
    }

    public void addStop(Stop stop) {
        String sql = "INSERT INTO stops (stop_name, address) VALUES (?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, stop.getStopName());
            ps.setString(2, stop.getAddress());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Stop getStopById(int id) {
        String sql = "SELECT * FROM stops WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Stop stop = new Stop();
                    stop.setId(rs.getInt("id"));
                    stop.setStopName(rs.getString("stop_name"));
                    stop.setAddress(rs.getString("address"));
                    return stop;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStop(Stop stop) {
        String sql = "UPDATE stops SET stop_name = ?, address = ? WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, stop.getStopName());
            ps.setString(2, stop.getAddress());
            ps.setInt(3, stop.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStop(int id) {
        String deleteRouteStopsSql = "DELETE FROM route_stops WHERE stop_id = ?";

        String deleteStopSql = "DELETE FROM stops WHERE id = ?";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psRouteStops = conn.prepareStatement(deleteRouteStopsSql)) {
                psRouteStops.setInt(1, id);
                psRouteStops.executeUpdate();

                try (PreparedStatement psStop = conn.prepareStatement(deleteStopSql)) {
                    psStop.setInt(1, id);
                    psStop.executeUpdate();
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
