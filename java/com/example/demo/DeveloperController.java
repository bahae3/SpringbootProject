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
    @GetMapping("/homeUser")
    public String homeUser(HttpSession session, Model model) {
        // Retrieve user from session
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "user/homePage";
    }

    // Edit profile section
    @GetMapping("/editProfile")
    public String editProfile(HttpSession session, Model model) {
        // Retrieve user from session
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "user/editProfile";
    }

    @PostMapping("/profileFormSubmission")
    public String editProfileSubmission(HttpServletRequest request, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        String firstName = request.getParameter("editfName");
        String lastName = request.getParameter("editlName");
        String email = request.getParameter("editEmail");
        String password = request.getParameter("editPwd");

        if (User.updateUser(user.getId(), firstName, lastName, email, password)) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            return "redirect:/editProfile";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("error", "Error updating profile.");
            return "user/editProfile";
        }
    }

    // Adding dev's technologies
    @GetMapping("/addTech")
    public String addTech(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        LinkedList<Competence> competences = Competence.getCompetences(user.getId());
        if (competences.isEmpty()) {
            model.addAttribute("competencesError", "Add a new skill.");
        } else {
            model.addAttribute("competences", competences);
        }
        return "user/addTechnology";
    }

    @PostMapping("/addTechForm")
    public String addTechForm(HttpSession session, HttpServletRequest request, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        String technology = request.getParameter("technology").toLowerCase(); // lowercase to make it easy for filter search for admin
        int yearsExperience = Integer.parseInt(request.getParameter("yearsExp"));
        int idUser = user.getId();

        if (Competence.addNewTechnology(technology, yearsExperience, idUser)){
            return "redirect:/addTech";
        } else {
            model.addAttribute("error", "Error adding this skill.");
            return "user/addTechnology";
        }
    }

    // Delete a technology
    @GetMapping("/deleteTech")
    public String deleteTech(@RequestParam("idTech") int id, Model model) {
        if (Competence.deleteTechnology(id)) {
            return "redirect:/addTech";
        } else {
            model.addAttribute("errorDelete", "Error deleting this skill.");
            return "user/addTechnology";
        }
    }


    // Dev projects section
    @GetMapping("/yourProjects")
    public String yourProjects(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        LinkedList<Project> projects = Project.getProjectsOfDev(user.getId());

        if (projects.isEmpty()) {
            model.addAttribute("projectsError", "There are no projects to show.");
        } else{
            model.addAttribute("projects", projects);
        }
        return "user/projects";
    }

    // Feedbacks about dev
    @GetMapping("/feedbacks")
    public String feedbacks(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }
        LinkedList<Project> projects = Project.getProjectsOfDev(user.getId());

        if (projects.isEmpty()) {
            model.addAttribute("projectsError", "There are no projects to show.");
        } else{
            model.addAttribute("projects", projects);
        }
        return "user/feedbacks";
    }
}
