/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.schoolname.schoolbusmanagementsystem.controller;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.BusDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.BusIssueDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.model.Bus;
import vn.edu.schoolname.schoolbusmanagementsystem.model.BusIssue;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

@Controller
@RequestMapping("/driver")
public class DriverController {

    private final BusDAO busDAO = new BusDAO();
    private final BusIssueDAO busIssueDAO = new BusIssueDAO();

    @GetMapping("/report-issue")
    public String showReportIssueForm(Model model) {
        List<Bus> buses = busDAO.getAllBuses();
        model.addAttribute("buses", buses);
        return "driver/report-issue";
    }

    @PostMapping("/report-issue")
    public String handleReportIssue(@RequestParam("busId") int busId,
            @RequestParam("issueDescription") String description,
            HttpSession session) {

        User driver = (User) session.getAttribute("user");

        Bus bus = new Bus();
        bus.setId(busId);

        BusIssue newIssue = new BusIssue();
        newIssue.setBus(bus);
        newIssue.setReportedBy(driver);
        newIssue.setIssueDescription(description);

        busIssueDAO.addIssue(newIssue);

        return "redirect:/driver/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDriverDashboard(HttpSession session, Model model) {
        User driver = (User) session.getAttribute("user");

        List<BusIssue> issues = busIssueDAO.getIssuesByDriverId(driver.getId());

        model.addAttribute("issues", issues);

        return "driver/dashboard";
    }

}
