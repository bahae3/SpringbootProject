package com.example.demo;

import Model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class LoginSignup {
    // Login section
    @GetMapping("/")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/loginFormSubmission")
    public String loginFormSubmission(HttpServletRequest request, Model model) {
        String email = request.getParameter("loginEmail");
        String password = request.getParameter("loginPwd");

        User user = User.userLoginAuth(email, password);

        if (user != null) {
            // Store user information in the session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
//            session.setMaxInactiveInterval(5 * 60 * 60);
            if (user.getIsAdmin() == 1) {
                return "admin/adminPage";
            } else {
                model.addAttribute("user", user);
                return "user/homePage";
            }
        }

        model.addAttribute("error", "Invalid email or password");

        return "redirect:/loginForm";
    }

    // Signup section
    @GetMapping("/signupForm")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signupFormSubmission")
    public String signupFormSubmission(HttpServletRequest request) {
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (User.addNewUser(firstName, lastName, email, password)) return "homePage";

        return "redirect:/signupForm";
    }
}
