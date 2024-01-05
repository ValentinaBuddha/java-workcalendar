package com.example.workcalendar.calendar.service;

import com.example.workcalendar.calendar.dto.DayDto;
import com.example.workcalendar.calendar.dto.MonthWorkDays;
import com.example.workcalendar.calendar.dto.MonthWorkHours;
import com.example.workcalendar.calendar.model.Day;
import com.example.workcalendar.calendar.model.DayMapper;
import com.example.workcalendar.calendar.repository.CalendarRepository;
import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.model.UserMapper;
import com.example.workcalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import static com.example.workcalendar.calendar.model.DayStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;

    @Override
    public DayDto addSickday(Long userId, LocalDate date) {
        log.info("Добавить больничный на дату {}", date);
        getUser(userId);
        Day day = getDay(date);
        day.setStatus(SICKDAY);
        return DayMapper.dayDto(day);
    }

    @Override
    public DayDto addLongholiday(Long userId, LocalDate date) {
        log.info("Добавить отпуск на дату {}", date);
        getUser(userId);
        Day day = getDay(date);
        day.setStatus(LONGHOLIDAY);
        return DayMapper.dayDto(day);
    }

    @Override
    public MonthWorkHours getMonthWorkHours(Long userId, Integer mm, Integer year) {
        log.info("Подсчет рабочего времени за месяц-{} год-{}", mm, year);
        MonthWorkHours workHours = new MonthWorkHours();
        User employee = getUser(userId);
        workHours.setEmployee(UserMapper.toUserShortDto(employee));
        String month = Month.of(mm).getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"));
        workHours.setMonth(month);
        MonthWorkDays workdays = calendarRepository.findWorkdaysInOneMonth(mm, year);
        workHours.setHours(workdays.getDays() * 8);
        return workHours;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", User.class)));
    }

    private Day getDay(LocalDate date) {
        return calendarRepository.findById(date).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", Day.class)));
    }
}