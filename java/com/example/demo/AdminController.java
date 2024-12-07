package com.example.demo;

import Model.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.LinkedList;

@Controller
public class AdminController {
    // Home of admin side (say "Chef de Projet" side)
    @GetMapping("/homeAdmin")
    public String homeUser(HttpSession session, Model model) {
        // Retrieve user from session
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "admin/adminPage";
    }

    // All projects path
    @GetMapping("/allProjects")
    public String allProjects(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        LinkedList<Project> projects = Project.getProjects();
        model.addAttribute("projects", projects);
        return "admin/allProjects";
    }

    @GetMapping("/deleteProject")
    public String deleteProject(@RequestParam("idProject") int id, Model model) {
        if (Project.deleteProject(id)) {
            return "redirect:/allProjects";
        } else {
            model.addAttribute("errorDelete", "Can't delete project.");
            return "admin/allProjects";
        }
    }

    // Create a project, and then assign devs to projects
    @GetMapping("/createProject")
    public String createProject(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        return "admin/createProject";
    }

    @PostMapping("createProjectForm")
    public String createProjectForm(HttpSession session, HttpServletRequest request, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        String title = request.getParameter("title");
        int duration = Integer.parseInt(request.getParameter("duration"));
        String description = request.getParameter("description");

        if (Project.createProject(title, duration, description)){
            return "redirect:/allProjects";
        } else {
            model.addAttribute("errorCreate", "Can't create project.");
            return "admin/createProject";
        }
    }

    // Search projects by skill (filter)
    @GetMapping("/searchBySkill")
    public String searchBySkill(Model model) {
        return "admin/searchBySkill";
    }

    // Evaluate developers within each project
    @GetMapping("/evaluateDevs")
    public String evaluateDevs(Model model) {
        return "admin/evaluateDevs";
    }
}
