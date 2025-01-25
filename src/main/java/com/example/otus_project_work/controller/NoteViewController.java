package com.example.otus_project_work.controller;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
//@RequestMapping("/")
public class NoteViewController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getAllNotes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        model.addAttribute("notes", userService.findByUsername(currentUsername).getNotes());
        return "index"; // Название шаблона без расширения .html
    }

    @GetMapping("/notes/new")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "new-note"; // Название шаблона для формы добавления заметки
    }

    @PostMapping("/notes/create")
    public String createNote(@ModelAttribute Note note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        var currentUser = userService.findByUsername(currentUsername);
        note.setAuthor(currentUser);
        note.setCreatedAt(LocalDateTime.now());
        noteService.save(note);
        return "redirect:/";
    }


    @GetMapping("/notes/edit/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        Note note = noteService.findById(id);
        model.addAttribute("note", note);
        return "edit-note"; // Название шаблона для формы редактирования заметки
    }

    @PostMapping("/notes/edit")
    public String editNote(@ModelAttribute Note note) {
        //????? добавить поле даты последнего редактирования
        noteService.save(note);
        return "redirect:/";
    }

    @PostMapping("/notes/delete/{id}")//???Переделать на DELETEMAPPING
    public String deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return "redirect:/";
    }
}
