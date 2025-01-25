package com.example.otus_project_work.service;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.entity.User;
import com.example.otus_project_work.repository.NoteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final ObjectMapper objectMapper;

    public NoteService(NoteRepository noteRepository,
                       ObjectMapper objectMapper) {
        this.noteRepository = noteRepository;
        this.objectMapper = objectMapper;
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note findById(Long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        return noteOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Заметка с id `%s` не найдена".formatted(id)));
    }

    public List<Note> findAllById(List<Long> ids) {
        return noteRepository.findAllById(ids);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public Note patch(Long id, JsonNode patchNode) throws IOException {
        Note note = noteRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Заметка с id `%s` не найдена".formatted(id)));

        objectMapper.readerForUpdating(note).readValue(patchNode);

        return noteRepository.save(note);
    }

    public List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException {
        Collection<Note> notes = noteRepository.findAllById(ids);

        for (Note note : notes) {
            objectMapper.readerForUpdating(note).readValue(patchNode);
        }

        List<Note> resultNotes = noteRepository.saveAll(notes);
        return resultNotes.stream()
                .map(Note::getId)
                .toList();
    }

    public Note delete(Long id) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null) {
            noteRepository.delete(note);
        }
        return note;
    }

    public void deleteMany(List<Long> ids) {
        noteRepository.deleteAllById(ids);
    }
}
