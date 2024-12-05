package com.example.demo;

import Model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.LinkedList;

@Controller
public class DeveloperController {
    // Edit profile section
    @GetMapping("/editProfile")
    public String editProfile(HttpSession session, Model model) {
        // Retrieve user from session
        User user = (User) session.getAttribute("loggedInUser");
        System.out.println(user);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "user/editProfile";
    }

    @PostMapping("/profileFormSubmission")
    public String editProfileSubmission(HttpServletRequest request){
        return "redirect:/editProfile";
    }

    // Adding dev's technologies
    @GetMapping("/addTech")
    public String addTech(HttpSession session,Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        LinkedList<Competence> competences = Competence.getCompetences(user.getId());
        model.addAttribute("competences", competences);
        return "user/addTechnology";
    }

    @PostMapping("/addTechForm")
    public String addTechForm(Model model) {

        return "redirect:/addTechForm";
    }


    // Dev projects section
    @GetMapping("/yourProjects")
    public String yourProjects() {
        return "user/projects";
    }

    // Feedbacks about dev
    @GetMapping("/feedbacks")
    public String feedbacks() {
        return "user/feedbacks";
    }
}
