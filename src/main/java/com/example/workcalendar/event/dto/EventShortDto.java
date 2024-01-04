package com.example.workcalendar.event.dto;

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
public class EventShortDto {

    Long id;

    String title;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime start;
}
