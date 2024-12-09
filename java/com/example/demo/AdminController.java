package com.example.demo;

import Model.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.LinkedList;

@Controller
public class AdminController {
    String[] roles = {"Front-end Developer", "Back-end Developer", "Database Administrator", "Full Stack Developer", "Tester", "Cloud Engineer", "DevOps Engineer", "OS Administrator"};
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
        System.out.println(projects);
        model.addAttribute("projects", projects);
        return "admin/allProjects";
    }

    // Assign developers to projects and make their roles
    @GetMapping("/assignDev")
    public String assignDev(@RequestParam("idProject") int id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        Project project = Project.getProjectById(id);
        LinkedList<User> users = User.getAllDevelopers();

        model.addAttribute("project", project);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin/assignDevs";
    }

    @PostMapping("/assignDevForm")
    public String assignDevForm(HttpSession session, HttpServletRequest request, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        int idUser = Integer.parseInt(request.getParameter("developer"));
        int idProject = Integer.parseInt(request.getParameter("projectId"));
        String role = request.getParameter("role");

        if (User.assignRoleToDev(idUser, role) && Project.assignDevToProject(idProject, idUser)) {
            return "redirect:/allProjects";
        }

        return "redirect:/assignDevForm";
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
    public String searchBySkill(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        return "admin/searchBySkill";
    }

    @PostMapping("/searchBySkillForm")
    public String searchBySkillForm(HttpSession session, HttpServletRequest request, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        String skill = request.getParameter("skill").toLowerCase();
        LinkedList<User> users = User.getUsersBySkill(skill);
        model.addAttribute("users", users);
        return "admin/searchBySkill";
    }

    // Evaluate developers within each project
    @GetMapping("/evaluateDevs")
    public String evaluateDevs(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        LinkedList<UserProject> usersAndProjects = UserProject.getUsersAndProjects();
        model.addAttribute("usersAndProjects", usersAndProjects);
        return "admin/evaluateDevs";
    }

    @GetMapping("/evaluateSingleDev")
    public String evaluateSingleDev(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        LinkedList<UserProject> usersAndProjects = UserProject.getUsersAndProjects();
        model.addAttribute("usersAndProjects", usersAndProjects);
        return "admin/evaluateDevs";
    }

}
