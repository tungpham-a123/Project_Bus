package vn.edu.schoolname.schoolbusmanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.AttendanceDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.RegistrationDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.RouteDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.StudentDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.TripDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Attendance;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Registration;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Route;
import vn.edu.schoolname.schoolbusmanagementsystem.model.RouteStop;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Student;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Trip;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

@Controller
@RequestMapping("/parent")
public class ParentController {

    private final StudentDAO studentDAO = new StudentDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final RouteDAO routeDAO = new RouteDAO();
    private final TripDAO tripDAO = new TripDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    @GetMapping("/dashboard")
    public String showParentDashboard(HttpSession session, Model model) {
        User parent = (User) session.getAttribute("user");
        List<Student> students = studentDAO.getStudentsByParentId(parent.getId());

        for (Student student : students) {
            Registration registration = registrationDAO.getRegistrationByStudentId(student.getId());
            if (registration != null) {
                Trip todaysTrip = tripDAO.getTodaysTripByStudentId(student.getId(), "pickup");
                if (todaysTrip != null) {
                    Attendance attendance = attendanceDAO.getAttendanceStatus(todaysTrip.getId(), student.getId());
                    registration.setTodaysTrip(todaysTrip);
                    registration.setAttendanceStatus(attendance);
                }
                student.setRegistration(registration);
            }
        }

        model.addAttribute("students", students);
        return "parent/dashboard";
    }

    

}
