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
    
}
