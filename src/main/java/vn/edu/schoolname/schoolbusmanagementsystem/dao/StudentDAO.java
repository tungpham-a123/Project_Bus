package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import vn.edu.schoolname.schoolbusmanagementsystem.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Attendance;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Registration;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Stop;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

public class StudentDAO {

    public List<Student> getStudentsByParentId(int parentId) {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE parent_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullName(rs.getString("full_name"));
                    student.setClassName(rs.getString("class_name"));
                    studentList.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    
}
