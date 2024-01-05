package com.example.workcalendar.calendar.dto;

import com.example.workcalendar.calendar.model.DayStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static com.example.workcalendar.utils.DateConstant.DATE_PATTERN;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DayDto {

    @JsonFormat(pattern = DATE_PATTERN)
    LocalDate date;

    DayStatus status;

    String description;
}
