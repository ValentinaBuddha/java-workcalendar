package com.example.workcalendar.event.dto;

import com.example.workcalendar.user.dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.example.workcalendar.utils.DateConstant.DATE_PATTERN;
import static com.example.workcalendar.utils.DateConstant.TIME_PATTERN;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {

    Long id;

    String title;

    @JsonFormat(pattern = DATE_PATTERN)
    LocalDate date;

    @JsonFormat(pattern = TIME_PATTERN)
    LocalTime start;

    @JsonFormat(pattern = TIME_PATTERN)
    LocalTime end;

    String location;

    String description;

    UserShortDto initiator;
}
