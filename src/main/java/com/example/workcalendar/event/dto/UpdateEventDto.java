package com.example.workcalendar.event.dto;

import com.example.workcalendar.utils.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.example.workcalendar.utils.DateConstant.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto {

    @Size(max = 255, groups = {Update.class})
    String title;

    @FutureOrPresent(groups = {Update.class})
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime start;

    @Future(groups = {Update.class})
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime end;

    @Size(max = 255, groups = {Update.class})
    String location;

    @Size(min = 20, max = 7000, groups = {Update.class})
    String description;
}
