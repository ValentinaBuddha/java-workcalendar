package com.example.workcalendar.user;

import com.example.workcalendar.user.controller.UserControllerPublic;
import com.example.workcalendar.user.dto.UserDto;
import com.example.workcalendar.user.dto.UserShortDto;
import com.example.workcalendar.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserControllerPublic.class)
class UserControllerPublicTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mvc;

    private final UserDto userDto = new UserDto(
            1L,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");
    private final UserShortDto userShortDto = new UserShortDto(1L, "Ivanov Ivan");

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userShortDto));

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(userShortDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].surName", is(userShortDto.getSurName())));
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(userDto);

        mvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }
}