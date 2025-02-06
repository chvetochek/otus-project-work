package com.example.otus_project_work.controller;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.entity.User;
import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteViewController.class)
class UserPagesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @MockitoBean
    private UserService userService;


    @DisplayName("Пользователь может зайти на главную страницу")
    @Test
    @WithMockUser()
    void shouldOpenIndexPage() throws Exception {
        User user = new User();
        user.setUsername("user");
        when(userService.findByUsername("user")).thenReturn(user);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @DisplayName("Пользователь может зайти на страницу добавления новой заметки")
    @Test
    @WithMockUser()
    void shouldOpenNewNotePage() throws Exception {
        User user = new User();
        user.setUsername("user");
        when(userService.findByUsername("user")).thenReturn(user);
        mockMvc.perform(get("/notes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new-note"));
    }

    @DisplayName("Пользователь может выполнить POST ")
    @Test
    @WithMockUser()
    void userShouldHaveAccessToPostNotePage() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Заметка 1");
        note.setContent("Содержимое заметки 1");
        when(noteService.findById(1L)).thenReturn(note);

        User user = new User();
        user.setUsername("user");
        when(userService.findByUsername("user")).thenReturn(user);

        mockMvc.perform(post("/notes/create").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", note.getTitle())
                .param("content", note.getContent()))
                .andExpect(status().isOk());
    }

    @DisplayName("Пользователь может зайти на страницу редактирования заметки")
    @Test
    @WithMockUser()
    void shouldOpenEditNotePage() throws Exception {
        when(noteService.findById(1L)).thenReturn(new Note());

        mockMvc.perform(get("/notes/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-note"));
    }

    @DisplayName("Пользователь может зайти на страницу заметки")
    @Test
    @WithMockUser()
    void shouldOpenNotePage() throws Exception {
        when(noteService.findById(1L)).thenReturn(new Note());
        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("note"));
    }
}