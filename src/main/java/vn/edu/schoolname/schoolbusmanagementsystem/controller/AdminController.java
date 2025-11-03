/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.schoolname.schoolbusmanagementsystem.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.BusDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.BusIssueDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.RoleDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.RouteDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.StopDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.StudentDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.TripDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.UserDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Bus;
import vn.edu.schoolname.schoolbusmanagementsystem.model.BusIssue;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Role;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Route;
import vn.edu.schoolname.schoolbusmanagementsystem.model.RouteStop;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Stop;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Student;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Trip;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

@Controller
@RequestMapping("/admin") 
public class AdminController {

    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private final RouteDAO routeDAO = new RouteDAO();
    private final StopDAO stopDAO = new StopDAO();
    private final BusDAO busDAO = new BusDAO();
    private final BusIssueDAO busIssueDAO = new BusIssueDAO();

    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin/dashboard"; 
    }
     @GetMapping("/stops")
    public String listStops(Model model) {
        List<Stop> stopList = stopDAO.getAllStops();
        model.addAttribute("stops", stopList);
        return "admin/stop-list"; 
    }

    @GetMapping("/stops/add")
    public String showAddStopForm() {
        return "admin/add-stop"; 
    }

    @PostMapping("/stops/add")
    public String addStop(@RequestParam("stopName") String stopName,
            @RequestParam("address") String address) {

        Stop newStop = new Stop();
        newStop.setStopName(stopName);
        newStop.setAddress(address);

        stopDAO.addStop(newStop);

        return "redirect:/admin/stops";
    }

    @GetMapping("/stops/edit/{id}")
    public String showEditStopForm(@PathVariable("id") int id, Model model) {
        Stop stop = stopDAO.getStopById(id);
        model.addAttribute("stop", stop);
        return "admin/edit-stop"; 
    }

    @PostMapping("/stops/edit")
    public String updateStop(@RequestParam("id") int id,
            @RequestParam("stopName") String stopName,
            @RequestParam("address") String address) {

        Stop stop = new Stop();
        stop.setId(id);
        stop.setStopName(stopName);
        stop.setAddress(address);

        stopDAO.updateStop(stop);

        return "redirect:/admin/stops";
    }

    @GetMapping("/stops/delete/{id}")
    public String deleteStop(@PathVariable("id") int id) {
        stopDAO.deleteStop(id);
        return "redirect:/admin/stops";
    }

   
}
