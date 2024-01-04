package com.example.workcalendar.user;

import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.model.UserMapper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class UserMapperTest {

    private final UserDto userDto = new UserDto(
            1L,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT"
    );
    private final User user = new User(
            1L,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT"
    );

    @Test
    public void toUserDto() {
        UserDto userDto = UserMapper.toUserDto(user);
        assertThat(userDto, equalTo(this.userDto));
    }

    @Test
    public void toUser() {
        User user = UserMapper.toUser(userDto);
        assertThat(user.getId(), equalTo(this.user.getId()));
        assertThat(user.getName(), equalTo(this.user.getName()));
        assertThat(user.getEmail(), equalTo(this.user.getEmail()));
    }
}
