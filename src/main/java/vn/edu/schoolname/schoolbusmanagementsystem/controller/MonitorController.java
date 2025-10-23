package vn.edu.schoolname.schoolbusmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.AttendanceDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.StudentDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.TripDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Student;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Trip;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

@Controller
@RequestMapping("/monitor")
public class MonitorController {

    private final TripDAO tripDAO = new TripDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    @GetMapping("/dashboard")
    public String showMonitorDashboard(HttpSession session, Model model) {
        User monitor = (User) session.getAttribute("user");

        List<Trip> todaysTrips = tripDAO.getTodaysTripsByMonitorId(monitor.getId());

        model.addAttribute("trips", todaysTrips);
        return "monitor/dashboard";
    }

    
}
