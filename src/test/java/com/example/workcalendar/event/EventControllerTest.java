package com.example.workcalendar.event;

import com.example.workcalendar.event.controller.EventController;
import com.example.workcalendar.event.dto.EventFullDto;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.event.dto.NewEventDto;
import com.example.workcalendar.event.dto.UpdateEventDto;
import com.example.workcalendar.event.service.EventService;
import com.example.workcalendar.user.dto.UserShortDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @MockBean
    private EventService eventService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    private final LocalDate date = LocalDate.of(2024, 12, 9);
    private final LocalTime start = LocalTime.of(13, 0, 0);
    private final NewEventDto newEventDto = new NewEventDto(
            "consultation",
            date,
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework");
    private final UpdateEventDto updateEventDto = new UpdateEventDto(
            "meeting",
            null,
            null,
            null,
            null,
            null);
    private final EventFullDto eventFullDto = new EventFullDto(
            1L,
            "consultation",
            date,
            start,
            start.plusHours(1),
            "office 25",
            "consultation about Spring Framework",
            new UserShortDto(1L, "Ivanov Ivan"));
    private final EventShortDto eventShortDto = new EventShortDto(1L, "consultation", start.atDate(date));

    @Test
    void addEvent() throws Exception {
        when(eventService.addEvent(anyLong(), any())).thenReturn(eventFullDto);

        mvc.perform(post("/users/1/events")
                        .content(mapper.writeValueAsString(newEventDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(eventFullDto)))
                .andExpect(jsonPath("$.id", is(eventFullDto.getId()), Long.class));
    }

    @Test
    void addEvent_whenDateIsWrong_thenThrownException() throws Exception {
        newEventDto.setTitle(null);

        mvc.perform(post("/users/1/events")
                        .content(mapper.writeValueAsString(newEventDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addEvent_whenBlankTitle_thenThrownException() throws Exception {
        newEventDto.setDate(LocalDate.of(1986,11,15));

        mvc.perform(post("/users/1/events")
                        .content(mapper.writeValueAsString(newEventDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEvent() throws Exception {
        eventFullDto.setTitle("meeting");
        when(eventService.updateEvent(anyLong(), anyLong(), any())).thenReturn(eventFullDto);

        mvc.perform(patch("/users/1/events/1")
                        .content(mapper.writeValueAsString(updateEventDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(eventFullDto)))
                .andExpect(jsonPath("$.id", is(eventFullDto.getId()), Long.class))
                .andExpect(jsonPath("$.title", is(eventFullDto.getTitle()), String.class));
    }

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