package com.example.otus_project_work.entity;

import java.time.LocalDateTime;

public record NoteDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime editedAt, UserDto author) {
}