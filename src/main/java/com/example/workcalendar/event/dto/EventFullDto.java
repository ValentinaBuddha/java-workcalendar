package com.example.workcalendar.event.dto;

import com.example.workcalendar.user.dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static com.example.workcalendar.utils.DateConstant.DATE_TIME_PATTERN;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {

    Long id;

    String title;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime start;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime end;

    String location;

    String description;

    UserShortDto initiator;
}
