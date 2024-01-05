package com.example.workcalendar.calendar;

import com.example.workcalendar.calendar.controller.CalendarController;
import com.example.workcalendar.calendar.dto.DayDto;
import com.example.workcalendar.calendar.dto.MonthWorkHours;
import com.example.workcalendar.calendar.service.CalendarService;
import com.example.workcalendar.user.dto.UserShortDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.example.workcalendar.calendar.model.DayStatus.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalendarController.class)
class CalendarControllerTest {

    @MockBean
    private CalendarService calendarService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    private final DayDto dayDto = new DayDto(LocalDate.of(2024, 1, 9), WORKDAY, null);
    private final MonthWorkHours workHours = new MonthWorkHours(new UserShortDto(1L, "Petrov Kirill"),
            "январь", 120L);

    @Test
    void addSickday() throws Exception {
        when(calendarService.addSickday(anyLong(), any())).thenReturn(dayDto);
        dayDto.setStatus(SICKDAY);

        mvc.perform(patch("/users/2/calendar/2024-01-09/sickday")
                        .content(mapper.writeValueAsString(dayDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dayDto)));
    }

    @Test
    void addLongholiday() throws Exception {
        when(calendarService.addSickday(anyLong(), any())).thenReturn(dayDto);
        dayDto.setStatus(LONGHOLIDAY);

        mvc.perform(patch("/users/2/calendar/2024-01-09/sickday")
                        .content(mapper.writeValueAsString(dayDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(dayDto)));
    }

    @Test
    void getMonthWorkHours() throws Exception {
        when(calendarService.getMonthWorkHours(anyLong(), anyInt(), anyInt())).thenReturn(workHours);

        mvc.perform(get("/users/1/calendar/workhours/1/2024"))
                .andExpect(status().isOk());
    }
}