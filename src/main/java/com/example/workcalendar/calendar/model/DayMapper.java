package com.example.workcalendar.calendar.model;

import com.example.workcalendar.calendar.dto.DayDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DayMapper {

    public DayDto dayDto(Day day) {
        if (day.getDescription() == null) {
            return DayDto.builder()
                    .date(day.getDate())
                    .status(day.getStatus())
                    .build();
        } else {
            return DayDto.builder()
                    .date(day.getDate())
                    .status(day.getStatus())
                    .description(day.getDescription())
                    .build();
        }
    }
}
