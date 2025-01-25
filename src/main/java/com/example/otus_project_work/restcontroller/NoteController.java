package com.example.otus_project_work.restcontroller;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.service.NoteService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAll() {
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    public Note getOne(@PathVariable Long id) {
        return noteService.findById(id);
    }

    @GetMapping("/by-ids")
    public List<Note> getMany(@RequestParam List<Long> ids) {
        return noteService.findAllById(ids);
    }

    @PostMapping("/create")
    public Note create(@RequestBody Note note) {
        return noteService.save(note);
    }

    @PatchMapping("/{id}")
    public Note patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        return noteService.patch(id, patchNode);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        return noteService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public Note delete(@PathVariable Long id) {
        return noteService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        noteService.deleteMany(ids);
    }
}
