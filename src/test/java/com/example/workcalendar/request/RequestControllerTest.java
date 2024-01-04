package com.example.workcalendar.request;

import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.request.controller.RequestController;
import com.example.workcalendar.request.dto.RequestDto;
import com.example.workcalendar.request.model.RequestStatus;
import com.example.workcalendar.request.service.RequestService;
import com.example.workcalendar.user.dto.UserShortDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.example.workcalendar.request.model.RequestStatus.*;

@WebMvcTest(controllers = RequestController.class)
class RequestControllerTest {

    @MockBean
    private RequestService requestService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    private final UserShortDto participant = new UserShortDto(2L, "Petrov Kirill");
    private final EventShortDto event = new EventShortDto(1L, "consultation",
            LocalDateTime.of(2024, 1, 25, 13, 0, 0));
    private final RequestDto requestDto = new RequestDto(1L, participant, event, RequestStatus.PENDING);

    @Test
    void addRequest() throws Exception {
        when(requestService.addRequest(anyLong(), anyLong(), anyLong())).thenReturn(requestDto);

        mvc.perform(post("/users/1/requests?eventId=2&participantId=2")
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(requestDto)))
                .andExpect(jsonPath("$.id", is(requestDto.getId()), Long.class));
    }

    @Test
    void addRequest_whenNoParticipantId_thenThrownException() throws Exception {
        mvc.perform(post("/users/1/requests?eventId=2")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void cancelRequest() throws Exception {
        when(requestService.cancelRequest(anyLong(), anyLong())).thenReturn(requestDto);
        requestDto.setStatus(CANCELED);

        mvc.perform(patch("/users/1/requests/1/cancel")
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(requestDto)));
    }

    @Test
    void confirmRequest() throws Exception {
        when(requestService.confirmRequest(anyLong(), anyLong())).thenReturn(requestDto);
        requestDto.setStatus(CONFIRMED);

        mvc.perform(patch("/users/2/requests/1/confirm")
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(requestDto)));
    }

    @Test
    void rejectRequest() throws Exception {
        when(requestService.rejectRequest(anyLong(), anyLong())).thenReturn(requestDto);
        requestDto.setStatus(REJECTED);

        mvc.perform(patch("/users/2/requests/1/reject")
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(requestDto)));
    }

    @Test
    void getAllByParticipant() throws Exception {
        when(requestService.getAllByParticipant(anyLong(), anyInt(), anyInt())).thenReturn(List.of(requestDto));

        mvc.perform(get("/users/2/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Long.class));
    }

    @Test
    void getAllByEvent() throws Exception {
        when(requestService.getAllByEvent(anyLong(), anyLong(), anyInt(), anyInt())).thenReturn(List.of(requestDto));

        mvc.perform(get("/users/2/requests/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(requestDto.getId()), Long.class));
    }
}