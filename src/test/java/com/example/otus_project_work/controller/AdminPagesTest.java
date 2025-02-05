package com.example.otus_project_work.controller;

import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.entity.User;
import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AdminIndexController.class)
class AdminPagesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @MockitoBean
    private UserService userService;


    @DisplayName("Пользователь может зайти на главную страницу admin")
    @Test
    @WithMockUser()
    void shouldOpenAdminIndexPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-index"));
    }

    @DisplayName("Пользователь может зайти на страницу просмотра всех заметок")
    @Test
    @WithMockUser()
    void shouldOpenAdminNotesPage() throws Exception {
        mockMvc.perform(get("/admin/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-notes"));
    }

    @DisplayName("Пользователь может зайти на страницу просмотра всех пользователей")
    @Test
    @WithMockUser()
    void shouldOpenAdminUsersPage() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-users"));
    }

    @DisplayName("Пользователь может зайти на страницу просмотра всех пользователей")
    @Test
    @WithMockUser()
    void shouldOpenAdminUserPage() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setNotes(Set.of(new Note()));
        when(userService.findOne(1L)).thenReturn(user);
        mockMvc.perform(get("/admin/users/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-user"));
    }
}
