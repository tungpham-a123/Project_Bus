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

   @GetMapping("/buses")
    public String listBuses(Model model) {
        List<Bus> busList = busDAO.getAllBuses();
        model.addAttribute("buses", busList);
        return "admin/bus-list";
    }

    @GetMapping("/buses/add")
    public String showAddBusForm() {
        return "admin/add-bus"; 
    }

    @PostMapping("/buses/add")
    public String addBus(@RequestParam("licensePlate") String licensePlate,
            @RequestParam("capacity") int capacity,
            @RequestParam("status") String status) {

        Bus newBus = new Bus();
        newBus.setLicensePlate(licensePlate);
        newBus.setCapacity(capacity);
        newBus.setStatus(status);

        busDAO.addBus(newBus);

        return "redirect:/admin/buses";
    }

    @GetMapping("/buses/edit/{id}")
    public String showEditBusForm(@PathVariable("id") int id, Model model) {
        Bus bus = busDAO.getBusById(id);
        model.addAttribute("bus", bus);
        return "admin/edit-bus";
    }

    @PostMapping("/buses/edit")
    public String updateBus(@RequestParam("id") int id,
            @RequestParam("licensePlate") String licensePlate,
            @RequestParam("capacity") int capacity,
            @RequestParam("status") String status) {

        Bus bus = new Bus();
        bus.setId(id);
        bus.setLicensePlate(licensePlate);
        bus.setCapacity(capacity);
        bus.setStatus(status);

        busDAO.updateBus(bus);

        return "redirect:/admin/buses";
    }

    @GetMapping("/buses/delete/{id}")
    public String deleteBus(@PathVariable("id") int id) {
        busDAO.deleteBus(id);
        return "redirect:/admin/buses";
    }
}
