package com.example.workcalendar.user;

import com.example.workcalendar.user.dto.UserDto;
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
}
