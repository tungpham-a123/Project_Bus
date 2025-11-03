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

    //khang
       @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> userList = userDAO.getAllUsers();
        model.addAttribute("users", userList);
        return "admin/user-list"; 
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        List<Role> roles = roleDAO.getAllRoles();
        model.addAttribute("roles", roles);
        return "admin/add-user"; 
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("roleId") int roleId) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); 
        newUser.setFullName(fullName);
        newUser.setPhone(phone);
        newUser.setEmail(email);

        Role role = new Role();
        role.setId(roleId);
        newUser.setRole(role);

        userDAO.addUser(newUser);

        return "redirect:/admin/users";
    }

        @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") int id, Model model) {
        User user = userDAO.getUserById(id);
        List<Role> roles = roleDAO.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "admin/edit-user"; 
    }

    @PostMapping("/users/edit")
    public String updateUser(@RequestParam int id,
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam int roleId) {

        User user = new User();
        user.setId(id);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);

        Role role = new Role();
        role.setId(roleId);
        user.setRole(role);

        userDAO.updateUser(user);

        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userDAO.deleteUser(id);
        return "redirect:/admin/users";
    }
    private final TripDAO tripDAO = new TripDAO();

    @GetMapping("/trips")
    public String listTrips(Model model) {
        List<Trip> tripList = tripDAO.getAllTrips();
        model.addAttribute("trips", tripList);
        return "admin/trip-list";
    }

    @GetMapping("/trips/add")
    public String showAddTripForm(Model model) {
        List<Route> routes = routeDAO.getAllRoutes();
        List<Bus> buses = busDAO.getAllBuses();
        List<User> drivers = userDAO.getUsersByRole("driver");
        List<User> monitors = userDAO.getUsersByRole("monitor");

        model.addAttribute("routes", routes);
        model.addAttribute("buses", buses);
        model.addAttribute("drivers", drivers);
        model.addAttribute("monitors", monitors);

        return "admin/add-trip";
    }

    @PostMapping("/trips/add")
    public String addTrip(@RequestParam("tripDate") String tripDateStr,
            @RequestParam("tripType") String tripType,
            @RequestParam("routeId") int routeId,
            @RequestParam("busId") int busId,
            @RequestParam("driverId") int driverId,
            @RequestParam("monitorId") int monitorId) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date tripDate = formatter.parse(tripDateStr);

        Trip newTrip = new Trip();
        newTrip.setTripDate(tripDate);
        newTrip.setTripType(tripType);

        Route route = new Route();
        route.setId(routeId);
        newTrip.setRoute(route);

        Bus bus = new Bus();
        bus.setId(busId);
        newTrip.setBus(bus);

        User driver = new User();
        driver.setId(driverId);
        newTrip.setDriver(driver);

        User monitor = new User();
        monitor.setId(monitorId);
        newTrip.setMonitor(monitor);

        tripDAO.addTrip(newTrip);

        return "redirect:/admin/trips";
    }

    @GetMapping("/trips/edit/{id}")
    public String showEditTripForm(@PathVariable("id") int id, Model model) {
        Trip trip = tripDAO.getTripById(id);

        List<Route> routes = routeDAO.getAllRoutes();
        List<Bus> buses = busDAO.getAllBuses();
        List<User> drivers = userDAO.getUsersByRole("driver");
        List<User> monitors = userDAO.getUsersByRole("monitor");

        model.addAttribute("trip", trip);
        model.addAttribute("routes", routes);
        model.addAttribute("buses", buses);
        model.addAttribute("drivers", drivers);
        model.addAttribute("monitors", monitors);

        return "admin/edit-trip";
    }

    @PostMapping("/trips/edit")
    public String updateTrip(@RequestParam("id") int id,
            @RequestParam("tripDate") String tripDateStr,
            @RequestParam("tripType") String tripType,
            @RequestParam("routeId") int routeId,
            @RequestParam("busId") int busId,
            @RequestParam("driverId") int driverId,
            @RequestParam("monitorId") int monitorId) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date tripDate = formatter.parse(tripDateStr);

        Trip tripToUpdate = new Trip();
        tripToUpdate.setId(id);
        tripToUpdate.setTripDate(tripDate);
        tripToUpdate.setTripType(tripType);

        Route route = new Route();
        route.setId(routeId);
        tripToUpdate.setRoute(route);

        Bus bus = new Bus();
        bus.setId(busId);
        tripToUpdate.setBus(bus);

        User driver = new User();
        driver.setId(driverId);
        tripToUpdate.setDriver(driver);

        User monitor = new User();
        monitor.setId(monitorId);
        tripToUpdate.setMonitor(monitor);

        tripDAO.updateTrip(tripToUpdate);

        return "redirect:/admin/trips";
    }

    @GetMapping("/trips/delete/{id}")
    public String deleteTrip(@PathVariable("id") int id) {
        tripDAO.deleteTrip(id);
        return "redirect:/admin/trips";
    }
}
