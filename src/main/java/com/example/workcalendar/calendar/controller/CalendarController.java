package com.example.workcalendar.calendar.controller;

import com.example.workcalendar.calendar.service.CalendarService;
import com.example.workcalendar.calendar.dto.DayDto;
import com.example.workcalendar.calendar.dto.MonthWorkHours;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.workcalendar.utils.DateConstant.DATE_PATTERN;

@Validated
@Slf4j
@RestController
@RequestMapping("/users/{userId}/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PatchMapping("/{date}/sickday")
    public DayDto addSickday(@PathVariable Long userId,
                             @PathVariable @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date) {
        log.info("PATCH / date {} sickday", date);
        return calendarService.addSickday(userId, date);
    }

    @PatchMapping("/{date}/longholiday")
    public DayDto addLongholiday(@PathVariable Long userId,
                                 @PathVariable @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date) {
        log.info("PATCH / date {} sickday", date);
        return calendarService.addLongholiday(userId, date);
    }

    @GetMapping("/workhours/{mm}/{year}")
    public MonthWorkHours getMonthWorkHours(@PathVariable Long userId, @PathVariable Integer mm,
                                            @PathVariable Integer year) {
        log.info("GET / work hours in month-{} year-{}", mm, year);
        return calendarService.getMonthWorkHours(userId, mm, year);
    }
}