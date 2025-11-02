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
import vn.edu.schoolname.schoolbusmanagementsystem.model.Bus;

public class BusDAO {

    public List<Bus> getAllBuses() {
        List<Bus> busList = new ArrayList<>();
        String sql = "SELECT * FROM buses ORDER BY id DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bus bus = new Bus();
                bus.setId(rs.getInt("id"));
                bus.setLicensePlate(rs.getString("license_plate"));
                bus.setCapacity(rs.getInt("capacity"));
                bus.setStatus(rs.getString("status"));
                busList.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busList;
    }

    public void addBus(Bus bus) {
        String sql = "INSERT INTO buses (license_plate, capacity, status) VALUES (?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bus.getLicensePlate());
            ps.setInt(2, bus.getCapacity());
            ps.setString(3, bus.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Bus getBusById(int id) {
        String sql = "SELECT * FROM buses WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bus bus = new Bus();
                    bus.setId(rs.getInt("id"));
                    bus.setLicensePlate(rs.getString("license_plate"));
                    bus.setCapacity(rs.getInt("capacity"));
                    bus.setStatus(rs.getString("status"));
                    return bus;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBus(Bus bus) {
        String sql = "UPDATE buses SET license_plate = ?, capacity = ?, status = ? WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bus.getLicensePlate());
            ps.setInt(2, bus.getCapacity());
            ps.setString(3, bus.getStatus());
            ps.setInt(4, bus.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBus(int busId) {
        String deleteAttendanceSql = "DELETE FROM attendance WHERE trip_id IN (SELECT id FROM trips WHERE bus_id = ?)";
        String deleteIssuesSql = "DELETE FROM bus_issues WHERE bus_id = ?";
        String deleteTripsSql = "DELETE FROM trips WHERE bus_id = ?";
        String deleteBusSql = "DELETE FROM buses WHERE id = ?";

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false); 

            try {
                try (PreparedStatement ps = conn.prepareStatement(deleteAttendanceSql)) {
                    ps.setInt(1, busId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteIssuesSql)) {
                    ps.setInt(1, busId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteTripsSql)) {
                    ps.setInt(1, busId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteBusSql)) {
                    ps.setInt(1, busId);
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
