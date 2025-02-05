package com.example.otus_project_work.controller;

import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminIndexController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String index() {
        return "admin-index";
    }

    @GetMapping("/admin/notes")
    public String getAllNotes(Model model) {
        model.addAttribute("notes", noteService.findAll());
        return "admin-notes";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin-users";
    }

    @GetMapping("/admin/users/{id}")
    public String getUser(@PathVariable Long id,  Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin-user";
    }
}
