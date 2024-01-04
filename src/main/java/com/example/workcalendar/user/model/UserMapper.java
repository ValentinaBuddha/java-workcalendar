package com.example.workcalendar.user.model;

import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.dto.UserShortDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getSurname(),
                user.getName(),
                user.getEmail(),
                user.getPosition(),
                user.getDepartment()
        );
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getSurname(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPosition(),
                userDto.getDepartment()
        );
    }

    public UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getSurname() + " " + user.getName()
        );
    }
}
