/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Role;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserDAO {

    public User checkLogin(String username, String password) {
        String sql = "SELECT u.*, r.role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.username = ? AND u.password = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));

                    Role role = new Role();
                    role.setId(rs.getInt("role_id"));
                    role.setRoleName(rs.getString("role_name"));

                    user.setRole(role);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT u.*, r.role_name FROM users u JOIN roles r ON u.role_id = r.id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));

                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));

                user.setRole(role);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
  // Regex đơn giản để validate email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    // Regex đơn giản để validate phone (10 số)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\d{10}$"
    );

    // Kiểm tra username đã tồn tại chưa
    public boolean isUsernameExist(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm người dùng với validate
    public String addUser(User user) {
        // Kiểm tra username
        if (isUsernameExist(user.getUsername())) {
            return "Tên đăng nhập đã tồn tại!";
        }
        // Kiểm tra email
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return "Email không hợp lệ!";
        }
        // Kiểm tra phone
        if (!PHONE_PATTERN.matcher(user.getPhone()).matches()) {
            return "Số điện thoại không hợp lệ!";
        }

        String sql = "INSERT INTO users (username, password, full_name, phone, email, role_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getRole().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi cơ sở dữ liệu khi thêm người dùng!";
        }

        return null; // null nghĩa là thành công
    }

    // Cập nhật người dùng với validate
    public String updateUser(User user) {
        // Kiểm tra email
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return "Email không hợp lệ!";
        }
        // Kiểm tra phone
        if (!PHONE_PATTERN.matcher(user.getPhone()).matches()) {
            return "Số điện thoại không hợp lệ!";
        }

        String sql = "UPDATE users SET full_name=?, phone=?, email=?, role_id=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getRole().getId());
            ps.setInt(5, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi cơ sở dữ liệu khi cập nhật người dùng!";
        }

        return null; // null nghĩa là thành công
    }

    public User getUserById(int id) {
        String sql = "SELECT u.*, r.role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));

                    Role role = new Role();
                    role.setId(rs.getInt("role_id"));
                    role.setRoleName(rs.getString("role_name"));

                    user.setRole(role);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsersByRole(String roleName) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT u.*, r.role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE r.role_name = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roleName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));

                    Role role = new Role();
                    role.setId(rs.getInt("role_id"));
                    role.setRoleName(rs.getString("role_name"));
                    user.setRole(role);

                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
