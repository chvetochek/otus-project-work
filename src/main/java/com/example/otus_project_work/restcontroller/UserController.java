package com.example.otus_project_work.restcontroller;

import com.example.otus_project_work.entity.User;
import com.example.otus_project_work.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @GetMapping("/by-ids")
    public List<User> getMany(@RequestParam List<Long> ids) {
        return userService.findMany(ids);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @PatchMapping("/{id}")
    public User patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        return userService.patch(id, patchNode);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        return userService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        userService.deleteMany(ids);
    }
}
