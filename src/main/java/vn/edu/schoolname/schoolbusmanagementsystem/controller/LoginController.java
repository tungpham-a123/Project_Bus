package vn.edu.schoolname.schoolbusmanagementsystem.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.schoolname.schoolbusmanagementsystem.dao.UserDAO;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

@Controller
public class LoginController {

    private final UserDAO userDAO = new UserDAO();

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = userDAO.checkLogin(username, password);

        if (user != null) {
            session.setAttribute("user", user);

            String roleName = user.getRole().getRoleName();
            if ("admin".equalsIgnoreCase(roleName)) {
                return "redirect:/admin/dashboard";
            } else if ("parent".equalsIgnoreCase(roleName)) {
                return "redirect:/parent/dashboard";
            } else if ("driver".equalsIgnoreCase(roleName)) { 
                return "redirect:/driver/dashboard";
            } else if ("monitor".equalsIgnoreCase(roleName)) { 
                return "redirect:/monitor/dashboard";
            }
            return "redirect:/"; 
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login"; 
    }
}
