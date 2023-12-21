package com.example.workcalendar.user;

import com.example.workcalendar.user.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class UserMapperTest {

    private final UserDto dto = new UserDto(1L, "Ivanov", "Ivan", "user@mail.ru", "admin",
            "IT");
    private final User us = new User(1L, "Ivanov", "Ivan", "user@mail.ru", "admin",
            "IT");

    @Test
    public void toUserDto() {
        UserDto userDto = UserMapper.toUserDto(us);
        assertThat(userDto, equalTo(dto));
    }

    @Test
    public void toUser() {
        User user = UserMapper.toUser(dto);
        assertThat(user.getId(), equalTo(us.getId()));
        assertThat(user.getName(), equalTo(us.getName()));
        assertThat(user.getEmail(), equalTo(us.getEmail()));
    }
}
