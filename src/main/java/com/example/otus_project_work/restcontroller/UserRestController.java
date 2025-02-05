package com.example.otus_project_work.restcontroller;

import com.example.otus_project_work.entity.User;
import com.example.otus_project_work.entity.UserDto;
import com.example.otus_project_work.entity.UserMapper;
import com.example.otus_project_work.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAll() {
        List<User> users = userService.findAll();
        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable Long id) {
        return userMapper.toUserDto(userService.findById(id));
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @PatchMapping("/{id}")
    public UserDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        return userMapper.toUserDto(userService.patch(id, patchNode));
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable Long id) {
        return userMapper.toUserDto(userService.delete(id));
    }
}
