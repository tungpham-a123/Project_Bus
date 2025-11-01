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

}
