package com.example.workcalendar.calendar;

import com.example.workcalendar.calendar.dto.DayDto;
import com.example.workcalendar.calendar.dto.MonthWorkDays;
import com.example.workcalendar.calendar.dto.MonthWorkHours;
import com.example.workcalendar.calendar.model.Day;
import com.example.workcalendar.calendar.repository.CalendarRepository;
import com.example.workcalendar.calendar.service.CalendarServiceImpl;
import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.model.UserMapper;
import com.example.workcalendar.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.workcalendar.calendar.model.DayStatus.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalendarServiceImplTest {

    @Mock
    private CalendarRepository calendarRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CalendarServiceImpl calendarService;

    private final LocalDate date = LocalDate.of(2024,1,9);
    private final User user = new User(
            1L,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");
    private final Day day = new Day(date, WORKDAY, null);
    private final DayDto dayDto = new DayDto(date, WORKDAY, null);
    private final MonthWorkDays days = new MonthWorkDays(15L);
    private final MonthWorkHours workHours = new MonthWorkHours(UserMapper.toUserShortDto(user),
            "январь", 120L);

    @Test
    void addSickday_whenUserFound_SickdayAdded() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calendarRepository.findById(date)).thenReturn(Optional.of(day));
        dayDto.setStatus(SICKDAY);

        DayDto actualDay = calendarService.addSickday(1L, date);

        Assertions.assertEquals(dayDto, actualDay);
    }

    @Test
    void addSickday_whenUserNotFound_thenThrownException() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> calendarService.addSickday(2L, date));
    }

    @Test
    void addLongholiday_whenDayFound_LongholidayAdded() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calendarRepository.findById(date)).thenReturn(Optional.of(day));
        dayDto.setStatus(LONGHOLIDAY);

        DayDto actualDay = calendarService.addLongholiday(1L, date);

        Assertions.assertEquals(dayDto, actualDay);
    }

    @Test
    void addLongholiday_whenDayNotFound_thenThrownException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calendarRepository.findById(date)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> calendarService.addLongholiday(1L, date));
    }

    @Test
    void getMonthWorkHours() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(calendarRepository.findWorkdaysInOneMonth(1, 2024)).thenReturn(days);

        MonthWorkHours actualHours = calendarService.getMonthWorkHours(1L, 1, 2024);

        Assertions.assertEquals(workHours, actualHours);
    }
}