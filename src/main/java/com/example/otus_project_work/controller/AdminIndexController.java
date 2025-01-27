package com.example.otus_project_work.controller;

import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminIndexController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String index(Model model) {
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
}
