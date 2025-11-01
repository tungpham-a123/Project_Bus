package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Bus;
import vn.edu.schoolname.schoolbusmanagementsystem.model.BusIssue;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

public class BusIssueDAO {

    public void addIssue(BusIssue issue) {
        String sql = "INSERT INTO bus_issues (bus_id, reported_by, issue_description, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, issue.getBus().getId());
            ps.setInt(2, issue.getReportedBy().getId());
            ps.setString(3, issue.getIssueDescription());
            ps.setString(4, "reported");

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BusIssue> getAllIssues() {
        List<BusIssue> issueList = new ArrayList<>();
        String sql = "SELECT bi.*, b.license_plate, u.full_name "
                + "FROM bus_issues bi "
                + "JOIN buses b ON bi.bus_id = b.id "
                + "JOIN users u ON bi.reported_by = u.id "
                + "ORDER BY bi.reported_date DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BusIssue issue = new BusIssue();
                issue.setId(rs.getInt("id"));
                issue.setIssueDescription(rs.getString("issue_description"));
                issue.setReportedDate(rs.getTimestamp("reported_date"));
                issue.setStatus(rs.getString("status"));

                Bus bus = new Bus();
                bus.setId(rs.getInt("bus_id"));
                bus.setLicensePlate(rs.getString("license_plate"));
                issue.setBus(bus);

                User driver = new User();
                driver.setId(rs.getInt("reported_by"));
                driver.setFullName(rs.getString("full_name"));
                issue.setReportedBy(driver);

                issueList.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issueList;
    }

    public void updateIssueStatus(int issueId, String newStatus) {
        String sql = "UPDATE bus_issues SET status = ? WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, issueId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BusIssue> getIssuesByDriverId(int driverId) {
        List<BusIssue> issueList = new ArrayList<>();
        String sql = "SELECT bi.*, b.license_plate, u.full_name "
                + "FROM bus_issues bi "
                + "JOIN buses b ON bi.bus_id = b.id "
                + "JOIN users u ON bi.reported_by = u.id "
                + "WHERE bi.reported_by = ? "
                + "ORDER BY bi.reported_date DESC";

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
