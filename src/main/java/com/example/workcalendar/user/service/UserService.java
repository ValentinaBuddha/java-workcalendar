package com.example.workcalendar.user.service;

import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.dto.UserShortDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    void deleteUser(Long id);

    List<UserShortDto> getAllUsers();

    UserDto getUserById(Long userId);
}
