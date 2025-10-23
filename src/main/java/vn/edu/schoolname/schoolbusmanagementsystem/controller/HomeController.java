package vn.edu.schoolname.schoolbusmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("message", "Chào mừng đến với hệ thống quản lý xe buýt!");
        return "home";
    }
}
