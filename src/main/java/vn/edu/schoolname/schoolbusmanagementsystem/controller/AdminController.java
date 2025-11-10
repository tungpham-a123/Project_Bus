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
