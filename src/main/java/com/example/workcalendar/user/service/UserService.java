package com.example.workcalendar.user.service;

import com.example.workcalendar.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto updateUser(long userId, UserDto userDto);

    void deleteUser(long id);

    List<UserDto> getAllUsers();

    UserDto getUserById(long userId);
}
