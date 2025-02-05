package com.example.otus_project_work.restcontroller;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.entity.NoteDto;
import com.example.otus_project_work.entity.NoteMapper;
import com.example.otus_project_work.service.NoteService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteRestController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteRestController(NoteService noteService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @GetMapping
    public List<NoteDto> getAll() {
        List<Note> notes = noteService.findAll();
        return notes.stream()
                .map(noteMapper::toNoteDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDto getOne(@PathVariable Long id) {
        return noteMapper.toNoteDto(noteService.findById(id));
    }

    @PostMapping
    public Note create(@RequestBody Note note) {
        return noteService.save(note);
    }

    @PatchMapping("/{id}")
    public NoteDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        return noteMapper.toNoteDto(noteService.patch(id, patchNode));
    }

    @DeleteMapping("/{id}")
    public NoteDto delete(@PathVariable Long id) {
        return noteMapper.toNoteDto(noteService.delete(id));
    }
}
