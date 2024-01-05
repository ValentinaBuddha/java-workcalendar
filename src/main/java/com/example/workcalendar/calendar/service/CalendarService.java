package com.example.workcalendar.calendar.service;

import com.example.workcalendar.calendar.dto.DayDto;
import com.example.workcalendar.calendar.dto.MonthWorkHours;

import java.time.LocalDate;

public interface CalendarService {

    DayDto addSickday(Long userId, LocalDate date);

    DayDto addLongholiday(Long userId, LocalDate date);

    MonthWorkHours getMonthWorkHours(Long userId, Integer mm, Integer year);
}
