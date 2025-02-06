package com.example.otus_project_work.security.noteviewcontroller;

import com.example.otus_project_work.config.SecurityConfig;
import com.example.otus_project_work.controller.AdminIndexController;
import com.example.otus_project_work.controller.NoteViewController;
import com.example.otus_project_work.entity.Note;
import com.example.otus_project_work.entity.User;
import com.example.otus_project_work.service.NoteService;
import com.example.otus_project_work.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminIndexController.class)
@Import(SecurityConfig.class)
class AdminPagesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @MockitoBean
    private UserService userService;

    @DisplayName("Пользователь с ролью USER не может зайти на страницу администратора")
    @Test
    @WithMockUser(roles = "USER")
    void userShouldHaveAccessToIndexPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Пользователь с ролью ADMIN может зайти на страницу администратора")
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldHaveAccessToAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @DisplayName("Анонимный пользователь не может зайти на страницу администратора")
    @Test
    @WithAnonymousUser()
    void anonymousShouldNotHaveAccessToIndexPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @DisplayName("Пользователь с ролью USER не может зайти на страницу просмотра всех заметок")
    @Test
    @WithMockUser(roles = "USER")
    void userShouldNotHaveAccessToNotesPage() throws Exception {
        mockMvc.perform(get("/admin/notes"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Пользователь с ролью ADMIN может зайти на страницу просмотра всех заметок")
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldHaveAccessToNotesPage() throws Exception {
        mockMvc.perform(get("/admin/notes"))
                .andExpect(status().isOk());
    }

    @DisplayName("Анонимный пользователь не может зайти на страницу просмотра всех заметок")
    @Test
    @WithAnonymousUser()
    void anonymousShouldNotHaveAccessToNotesPage() throws Exception {
        mockMvc.perform(get("/admin/notes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @DisplayName("Пользователь с ролью USER не может зайти на страницу просмотра всех пользователей")
    @Test
    @WithMockUser(roles = "USER")
    void userShouldNotHaveAccessToUsersPage() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Пользователь с ролью ADMIN может зайти на страницу просмотра всех пользователей")
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldHaveAccessToUsersPage() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk());
    }

    @DisplayName("Анонимный пользователь не может зайти на страницу просмотра всех пользователей")
    @Test
    @WithAnonymousUser()
    void anonymousShouldNotHaveAccessToUsersPage() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @DisplayName("Пользователь с ролью USER не может зайти на страницу просмотра конкретного пользователя")
    @Test
    @WithMockUser(roles = "USER")
    void userShouldNotHaveAccessToUserPage() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setNotes(Set.of(new Note()));
        when(userService.findById(1L)).thenReturn(user);
        mockMvc.perform(get("/admin/users/1"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Пользователь с ролью ADMIN может зайти на страницу просмотра конкретного пользователя")
    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldHaveAccessToUserPage() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setNotes(Set.of(new Note()));
        when(userService.findById(1L)).thenReturn(user);
        mockMvc.perform(get("/admin/users/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("Анонимный пользователь не может зайти на страницу просмотра конкретного пользователя")
    @Test
    @WithAnonymousUser()
    void anonymousShouldNotHaveAccessToUserPage() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setNotes(Set.of(new Note()));
        when(userService.findById(1L)).thenReturn(user);
        mockMvc.perform(get("/admin/users/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}