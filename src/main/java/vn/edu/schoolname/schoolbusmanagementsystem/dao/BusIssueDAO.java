package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import vn.edu.schoolname.schoolbusmanagementsystem.model.BusIssue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Bus;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

public class BusIssueDAO {

   

    public List<BusIssue> getIssuesByDriverId(int driverId) {
        List<BusIssue> issueList = new ArrayList<>();
        String sql = "SELECT bi.*, b.license_plate, u.full_name "
                + "FROM bus_issues bi "
                + "JOIN buses b ON bi.bus_id = b.id "
                + "JOIN users u ON bi.reported_by = u.id "
                + "WHERE bi.reported_by = ? "
                + 
                "ORDER BY bi.reported_date DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BusIssue issue = new BusIssue();
                    issue.setId(rs.getInt("id"));
                    issue.setIssueDescription(rs.getString("issue_description"));
                    issue.setReportedDate(rs.getTimestamp("reported_date"));
                    issue.setStatus(rs.getString("status"));

                    Bus bus = new Bus();
                    bus.setLicensePlate(rs.getString("license_plate"));
                    issue.setBus(bus);

                    User driver = new User();
                    driver.setFullName(rs.getString("full_name"));
                    issue.setReportedBy(driver);

                    issueList.add(issue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issueList;
    }
}
