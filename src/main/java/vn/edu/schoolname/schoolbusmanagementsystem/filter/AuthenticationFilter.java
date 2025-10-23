package vn.edu.schoolname.schoolbusmanagementsystem.filter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import vn.edu.schoolname.schoolbusmanagementsystem.model.User;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Cho phép truy cập các trang không cần đăng nhập
        if (path.startsWith("/login") || path.startsWith("/css/") || path.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        // Kiểm tra xem người dùng đã đăng nhập chưa
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            // Nếu đã đăng nhập, cho phép tiếp tục
            chain.doFilter(request, response);
        }
    }
}