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
    
}
