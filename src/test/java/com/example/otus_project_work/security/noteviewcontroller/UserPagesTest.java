package com.example.otus_project_work.security.noteviewcontroller;

import com.example.otus_project_work.config.SecurityConfig;
import com.example.otus_project_work.controller.NoteViewController;
import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.entity.User;
import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteViewController.class)
@Import(SecurityConfig.class)
class UserPagesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @MockitoBean
    private UserService userService;

    @DisplayName("Пользователь с ролью USER может зайти на главную страницу")
    @Test
    @WithMockUser(roles = "USER")
    void userShouldHaveAccessToIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @DisplayName("Пользователь с ролью ADMIN не может зайти на главную страницу")
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldNotHaveAccessToIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Анонимный пользователь не может зайти на главную страницу")
    @Test
    @WithAnonymousUser()
    void anonymousShouldNotHaveAccessToIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @DisplayName("Пользователь с ролью USER может зайти на страницу добавления новой заметки")
    @Test
    @WithMockUser(roles = "USER")
    void userShouldHaveAccessToNewNotePage() throws Exception {
        mockMvc.perform(get("/notes/new"))
                .andExpect(status().isOk());
    }

    @DisplayName("Пользователь с ролью ADMIN не может зайти на страницу добавления новой заметки")
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldNotHaveAccessToNewNotePage() throws Exception {
        mockMvc.perform(get("/notes/new"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Анонимный пользователь не может зайти на страницу добавления новой заметки")
    @Test
    @WithAnonymousUser()
    void anonymousShouldNotHaveAccessToNewNotePage() throws Exception {
        mockMvc.perform(get("/notes/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @DisplayName("Пользователь с ролью USER может выполнить POST ")
    @Test
    @WithUserDetails(value = "testuser",  userDetailsServiceBeanName = "UserDetails")
    void userShouldHaveAccessToPostNotePage() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Заметка 1");
        note.setContent("Содержимое заметки 1");
        when(noteService.findById(1L)).thenReturn(note);

        User user = new User();
        user.setUsername("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);

        mockMvc.perform(post("/notes/create").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", note.getTitle())
                .param("content", note.getContent()))
                .andExpect(status().isOk());
    }
    //+ patch + delete



    @DisplayName("Пользователь с ролью USER может зайти на страницу редактирования заметки")
    @Test
    @WithMockUser(roles = "USER")
    void userShouldHaveAccessToEditNotePage() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Заметка 1");
        note.setContent("Содержимое заметки 1");
        when(noteService.findById(1L)).thenReturn(note);

        mockMvc.perform(get("/notes/edit/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("Пользователь с ролью ADMIN не может зайти на страницу редактирования заметки")
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldNotHaveAccessToEditNotePage() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Заметка 1");
        note.setContent("Содержимое заметки 1");
        when(noteService.findById(1L)).thenReturn(note);

        mockMvc.perform(get("/notes/edit/1"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Пользователь с ролью USER может зайти на страницу заметки")
    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void userShouldHaveAccessToNotePage() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Заметка 1");
        note.setContent("Содержимое заметки 1");
        note.setAuthor(user);
        when(noteService.findById(1L)).thenReturn(note);
        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("Пользователь с ролью ADMIN не может зайти на страницу заметки")
    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    void adminShouldNotHaveAccessToNotePage() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);
        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isForbidden());
    }
}