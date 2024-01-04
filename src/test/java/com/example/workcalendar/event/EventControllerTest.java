package com.example.workcalendar.event;

import com.example.workcalendar.event.controller.EventController;
import com.example.workcalendar.event.dto.EventFullDto;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.event.service.EventService;
import com.example.workcalendar.user.dto.UserShortDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @MockBean
    private EventService eventService;
    @Autowired
    private MockMvc mvc;

    private final LocalDateTime start = LocalDateTime.of(2024, 1, 25, 13, 0, 0);
    private final EventFullDto eventFullDto = new EventFullDto(
            1L,
            "consultation",
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework",
            new UserShortDto(1L, "Ivanov Ivan"));
    private final EventShortDto eventShortDto = new EventShortDto(1L, "consultation", start);

    @Test
    void deleteEvent() throws Exception {
        mvc.perform(delete("/users/1/events/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(eventService, Mockito.times(1))
                .deleteEvent(anyLong(), anyLong());
    }

    @Test
    void getAllEventsByInitiator() throws Exception {
        when(eventService.getAllEventsByInitiator(any(), any(), any())).thenReturn(List.of(eventShortDto));

        mvc.perform(get("/users/1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(eventShortDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].title", is(eventShortDto.getTitle())));
    }

    @Test
    void getAllEventsByInitiator_whenBadPagingArguments_thenThrownException() throws Exception {
        mvc.perform(get("/users/1/events/?from=0&size=-1"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getEventById() throws Exception {
        when(eventService.getEventById(anyLong(), anyLong())).thenReturn(eventFullDto);

        mvc.perform(get("/users/1/events/1"))
                .andExpect(status().isOk());
    }
}