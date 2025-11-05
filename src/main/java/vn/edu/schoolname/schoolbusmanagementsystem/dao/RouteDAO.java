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
//khang
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
   
}
