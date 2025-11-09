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
import vn.edu.schoolname.schoolbusmanagementsystem.model.Attendance;
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

    @GetMapping("/trip/{tripId}")
    public String showAttendancePage(@PathVariable("tripId") int tripId, Model model) {
        Trip trip = tripDAO.getTripById(tripId);
        if (trip == null) {
            return "redirect:/monitor/dashboard";
        }
        List<Student> students = studentDAO.getStudentsForTrip(trip.getRoute().getId(), tripId);

        for (Student student : students) {
            if (student.getRegistration() != null) {
                Attendance currentTripAttendance = attendanceDAO.getAttendanceStatus(tripId, student.getId());

                student.getRegistration().setAttendanceStatus(currentTripAttendance);
            }
        }

        model.addAttribute("trip", trip);
        model.addAttribute("students", students);

        return "monitor/trip-attendance";
    }

    @PostMapping("/trip/end")
    public String endTrip(@RequestParam("tripId") int tripId) {
        tripDAO.updateTripStatus(tripId, "completed");
        return "redirect:/monitor/trip/" + tripId;
    }

    @PostMapping("/trip/check-in")
    public String handleCheckIn(@RequestParam("tripId") int tripId,
            @RequestParam("studentId") int studentId,
            @RequestParam("stopId") int stopId) {
        attendanceDAO.checkIn(tripId, studentId, stopId);
        return "redirect:/monitor/trip/" + tripId;
    }

    @PostMapping("/trip/check-out")
    public String handleCheckOut(@RequestParam("tripId") int tripId,
            @RequestParam("studentId") int studentId,
            @RequestParam("stopId") int stopId) {

        attendanceDAO.checkOut(tripId, studentId, stopId);

        return "redirect:/monitor/trip/" + tripId;
    }

    @PostMapping("/trip/start")
    public String startTrip(@RequestParam("tripId") int tripId) {
        tripDAO.updateTripStatus(tripId, "ongoing");
        return "redirect:/monitor/trip/" + tripId;
    }
}
