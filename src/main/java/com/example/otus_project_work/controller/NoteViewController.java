package com.example.otus_project_work.controller;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class NoteViewController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getAllNotes(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("notes", userService.findByUsername(userDetails.getUsername()).getNotes());
        return "index";
    }

    @GetMapping("/notes/new")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "new-note";
    }

    @PostMapping("/notes/create")
    public String createNote(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Note note) {
        var currentUser = userService.findByUsername(userDetails.getUsername());
        note.setAuthor(currentUser);
        note.setCreatedAt(LocalDateTime.now());
        noteService.save(note);
        return "redirect:/";
    }

    @GetMapping("/notes/edit/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        Note note = noteService.findById(id);
        model.addAttribute("note", note);
        return "edit-note";
    }

    @PatchMapping("/notes/{id}")
    public String editNote(@PathVariable Long id, @ModelAttribute Note note) {
        Note oldNote = noteService.findById(id);
        oldNote.setTitle(note.getTitle());
        oldNote.setContent(note.getContent());
        oldNote.setEditedAt(LocalDateTime.now());
        noteService.save(oldNote);
        return "redirect:/";
    }

    @DeleteMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return "redirect:/";
    }


    @GetMapping("/notes/{id}")
    @PreAuthorize("hasRole('ADMIN') or @noteService.findById(#id).author.id == authentication.principal.id")
    public String getNote(@PathVariable Long id,  Model model) {
        Note note = noteService.findById(id);
//        if (note == null) {
//            return
//        }
        model.addAttribute("note", note);
        return "note";
    }
}
