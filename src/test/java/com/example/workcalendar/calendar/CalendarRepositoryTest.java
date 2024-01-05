package com.example.workcalendar.calendar;

import com.example.workcalendar.calendar.dto.MonthWorkDays;
import com.example.workcalendar.calendar.model.Day;
import com.example.workcalendar.calendar.repository.CalendarRepository;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static com.example.workcalendar.calendar.model.DayStatus.WORKDAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class CalendarRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CalendarRepository calendarRepository;

    private final LocalDate date = LocalDate.of(2024, 1, 9);
    private final User user = new User(
            1L,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");
    private final Day day = new Day(date, WORKDAY, null);

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        calendarRepository.save(day);
    }

    @Test
    void findWorkdaysInOneMonth() {
        MonthWorkDays days = calendarRepository.findWorkdaysInOneMonth(1, 2024);

        assertThat(days.getDays(), equalTo(17L));
    }
}