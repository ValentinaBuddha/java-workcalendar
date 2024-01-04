package com.example.workcalendar.user.controller;

import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.service.UserService;
import com.example.workcalendar.utils.Create;
import com.example.workcalendar.utils.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("admin/users")
public class UserControllerAdmin {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto addUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("POST / users / {} / {}", userDto.getSurname(), userDto.getName());
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @Validated(Update.class) @RequestBody UserDto userDto) {
        log.info("PATCH / users / {}", userId);
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        log.info("DELETE / users / {}", userId);
        userService.deleteUser(userId);
    }
}

