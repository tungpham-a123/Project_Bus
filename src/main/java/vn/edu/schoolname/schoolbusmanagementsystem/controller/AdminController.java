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

    @GetMapping("/routes")
    public String listRoutes(Model model) {
        List<Route> routeList = routeDAO.getAllRoutes();
        model.addAttribute("routes", routeList);
        return "admin/route-list"; 
    }

    @GetMapping("/routes/add")
    public String showAddRouteForm() {
        return "admin/add-route";
    }

    @PostMapping("/routes/add")
    public String addRoute(@RequestParam("routeName") String routeName,
            @RequestParam("description") String description) {

        Route newRoute = new Route();
        newRoute.setRouteName(routeName);
        newRoute.setDescription(description);

        routeDAO.addRoute(newRoute);

        return "redirect:/admin/routes";
    }

    @GetMapping("/routes/edit/{id}")
    public String showEditRouteForm(@PathVariable("id") int id, Model model) {
        Route route = routeDAO.getRouteById(id);
        model.addAttribute("route", route);
        return "admin/edit-route";
    }

    @PostMapping("/routes/edit")
    public String updateRoute(@RequestParam("id") int id,
            @RequestParam("routeName") String routeName,
            @RequestParam("description") String description) {

        Route route = new Route();
        route.setId(id);
        route.setRouteName(routeName);
        route.setDescription(description);

        routeDAO.updateRoute(route);

        return "redirect:/admin/routes";
    }

    @GetMapping("/routes/delete/{id}")
    public String deleteRoute(@PathVariable("id") int id) {
        routeDAO.deleteRoute(id);
        return "redirect:/admin/routes"; 
    }
   
    @GetMapping("/routes/details/{id}")
    public String showRouteDetails(@PathVariable("id") int id, Model model) {
        Route route = routeDAO.getRouteById(id);
        List<RouteStop> stops = routeDAO.getStopsByRouteId(id);

        model.addAttribute("route", route);
        model.addAttribute("stops", stops);

        return "admin/route-details"; 
    }

    @GetMapping("/routes/{routeId}/add-stop")
    public String showAddStopToRouteForm(@PathVariable("routeId") int routeId, Model model) {
        Route route = routeDAO.getRouteById(routeId);
        List<Stop> availableStops = stopDAO.getAvailableStopsForRoute(routeId);

        model.addAttribute("route", route);
        model.addAttribute("availableStops", availableStops);

        return "admin/add-stop-to-route";
    }
    
    @PostMapping("/routes/add-stop")
    public String handleAddStopToRoute(@RequestParam("routeId") int routeId,
            @RequestParam("stopId") int stopId,
            @RequestParam("stopOrder") int stopOrder,
            @RequestParam("estimatedPickupTime") String estimatedPickupTimeStr) {

        Time estimatedTime = Time.valueOf(estimatedPickupTimeStr + ":00"); 
        routeDAO.addStopToRoute(routeId, stopId, stopOrder, estimatedTime);

        return "redirect:/admin/routes/details/" + routeId;
    }
}
