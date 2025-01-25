package com.example.otus_project_work.repository;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByAuthor(User author);
}
