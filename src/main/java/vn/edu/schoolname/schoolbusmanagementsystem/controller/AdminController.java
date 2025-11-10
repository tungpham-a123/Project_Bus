/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.schoolname.schoolbusmanagementsystem.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.schoolname.schoolbusmanagementsystem.dao.BusDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.BusIssueDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.RoleDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.RouteDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.StopDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.UserDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.model.BusIssue;

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

    @GetMapping("/issues")
    public String listBusIssues(Model model) {
        List<BusIssue> issueList = busIssueDAO.getAllIssues();
        model.addAttribute("issues", issueList);
        return "admin/issue-list";
    }

    @PostMapping("/issues/update-status")
    public String updateIssueStatus(@RequestParam("issueId") int issueId,
            @RequestParam("status") String status) {
        busIssueDAO.updateIssueStatus(issueId, status);
        return "redirect:/admin/issues";
    }
    private final StudentDAO studentDAO = new StudentDAO();

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> studentList = studentDAO.getAllStudents();
        model.addAttribute("students", studentList);
        return "admin/student-list";
    }

    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        List<User> parents = userDAO.getUsersByRole("parent");
        model.addAttribute("parents", parents);
        return "admin/add-student";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam("studentCode") String studentCode,
            @RequestParam("fullName") String fullName,
            @RequestParam("className") String className,
            @RequestParam("parentId") int parentId) {

        Student newStudent = new Student();
        newStudent.setStudentCode(studentCode);
        newStudent.setFullName(fullName);
        newStudent.setClassName(className);

        User parent = new User();
        parent.setId(parentId);
        newStudent.setParent(parent);

        studentDAO.addStudent(newStudent);

        return "redirect:/admin/students";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") int id, Model model) {
        Student student = studentDAO.getStudentById(id);
        List<Student> allStudents = studentDAO.getAllStudents();
        for (Student s : allStudents) {
            if (s.getId() == id) {
                student.setParent(s.getParent());
                break;
            }
        }

        List<User> parents = userDAO.getUsersByRole("parent");

        model.addAttribute("student", student);
        model.addAttribute("parents", parents);

        return "admin/edit-student";
    }

    @PostMapping("/students/edit")
    public String updateStudent(@RequestParam("id") int id,
            @RequestParam("studentCode") String studentCode,
            @RequestParam("fullName") String fullName,
            @RequestParam("className") String className,
            @RequestParam("parentId") int parentId) {

        Student studentToUpdate = new Student();
        studentToUpdate.setId(id);
        studentToUpdate.setStudentCode(studentCode);
        studentToUpdate.setFullName(fullName);
        studentToUpdate.setClassName(className);

        User parent = new User();
        parent.setId(parentId);
        studentToUpdate.setParent(parent);

        studentDAO.updateStudent(studentToUpdate);

        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        studentDAO.deleteStudent(id);
        return "redirect:/admin/students";
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
            @RequestParam("roleId") int roleId,
            Model model) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);
        Role role = new Role();
        role.setId(roleId);
        user.setRole(role);

        String error = userDAO.addUser(user);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("roles", roleDAO.getAllRoles());
            return "admin/add-user"; // trả lại form với thông báo lỗi
        }

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
            @RequestParam("monitorId") int monitorId,
            Model model) {

        model.addAttribute("routes", routeDAO.getAllRoutes());
        model.addAttribute("buses", busDAO.getAllBuses());
        model.addAttribute("drivers", userDAO.getUsersByRole("driver"));
        model.addAttribute("monitors", userDAO.getUsersByRole("monitor"));

        if (tripDateStr == null || tripDateStr.isEmpty()) {
            model.addAttribute("error", "Ngày chuyến đi không được để trống.");
            return "admin/add-trip";
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = formatter.parse(tripDateStr);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            if (routeDAO.getRouteById(routeId) == null
                    || busDAO.getBusById(busId) == null
                    || userDAO.getUserById(driverId) == null
                    || userDAO.getUserById(monitorId) == null) {
                model.addAttribute("error", "Dữ liệu không hợp lệ, vui lòng kiểm tra lại.");
                return "admin/add-trip";
            }

            if (tripDAO.isBusBusy(busId, sqlDate, null)) {
                model.addAttribute("error", "Xe này đã có chuyến khác vào ngày này.");
                return "admin/add-trip";
            }
            if (tripDAO.isDriverBusy(driverId, sqlDate, null)) {
                model.addAttribute("error", "Tài xế này đã có chuyến khác vào ngày này.");
                return "admin/add-trip";
            }

            Trip newTrip = new Trip();
            newTrip.setTripDate(utilDate);
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

        } catch (ParseException e) {
            model.addAttribute("error", "Ngày chuyến đi không hợp lệ.");
            return "admin/add-trip";
        }
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
    public String updateTrip(
            @RequestParam("id") int id,
            @RequestParam("tripDate") String tripDateStr,
            @RequestParam("tripType") String tripType,
            @RequestParam("routeId") int routeId,
            @RequestParam("busId") int busId,
            @RequestParam("driverId") int driverId,
            @RequestParam("monitorId") int monitorId,
            Model model) throws ParseException {

        // Validate ngày trip
        if (tripDateStr == null || tripDateStr.isEmpty()) {
            model.addAttribute("error", "Ngày chuyến đi không được để trống.");
            model.addAttribute("trip", tripDAO.getTripById(id));
            model.addAttribute("routes", routeDAO.getAllRoutes());
            model.addAttribute("buses", busDAO.getAllBuses());
            model.addAttribute("drivers", userDAO.getUsersByRole("driver"));
            model.addAttribute("monitors", userDAO.getUsersByRole("monitor"));
            return "admin/edit-trip";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = formatter.parse(tripDateStr);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // để check trùng lịch

        // Validate trùng lịch
        if (tripDAO.isBusBusy(busId, sqlDate, id)) {
            model.addAttribute("error", "Xe này đã có chuyến khác vào ngày này.");
            model.addAttribute("trip", tripDAO.getTripById(id));
            model.addAttribute("routes", routeDAO.getAllRoutes());
            model.addAttribute("buses", busDAO.getAllBuses());
            model.addAttribute("drivers", userDAO.getUsersByRole("driver"));
            model.addAttribute("monitors", userDAO.getUsersByRole("monitor"));
            return "admin/edit-trip";
        }

        if (tripDAO.isDriverBusy(driverId, sqlDate, id)) {
            model.addAttribute("error", "Tài xế này đã có chuyến khác vào ngày này.");
            model.addAttribute("trip", tripDAO.getTripById(id));
            model.addAttribute("routes", routeDAO.getAllRoutes());
            model.addAttribute("buses", busDAO.getAllBuses());
            model.addAttribute("drivers", userDAO.getUsersByRole("driver"));
            model.addAttribute("monitors", userDAO.getUsersByRole("monitor"));
            return "admin/edit-trip";
        }

        // Tạo Trip object để update
        Trip tripToUpdate = new Trip();
        tripToUpdate.setId(id);
        tripToUpdate.setTripDate(utilDate); // vẫn dùng java.util.Date
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
