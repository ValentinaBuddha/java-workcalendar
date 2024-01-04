package com.example.workcalendar.request.controller;

import com.example.workcalendar.request.dto.RequestDto;
import com.example.workcalendar.request.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RequestDto addRequest(@PathVariable Long userId, @RequestParam Long eventId,
                                 @RequestParam Long participantId) {
        log.info("POST / initiator {} event {} participant {}", userId, eventId, participantId);
        return requestService.addRequest(userId, eventId, participantId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("PATCH / initiator {} cancel request {}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }

    @PatchMapping("/{requestId}/confirm")
    public RequestDto confirmRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("PATCH / participant {} confirm request {}", userId, requestId);
        return requestService.confirmRequest(userId, requestId);
    }

    @PatchMapping("/{requestId}/reject")
    public RequestDto rejectRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("PATCH / participant {} reject request {}", userId, requestId);
        return requestService.rejectRequest(userId, requestId);
    }

    @GetMapping
    public List<RequestDto> getAllByParticipant(@PathVariable Long userId,
                                                @RequestParam(value = "from", defaultValue = "0")
                                                @PositiveOrZero Integer from,
                                                @RequestParam(value = "size", defaultValue = "10")
                                                @Positive Integer size) {
        log.info("GET / participant {}", userId);
        return requestService.getAllByParticipant(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public List<RequestDto> getAllByEvent(@PathVariable Long userId,
                                          @PathVariable Long eventId,
                                          @RequestParam(value = "from", defaultValue = "0")
                                          @PositiveOrZero Integer from,
                                          @RequestParam(value = "size", defaultValue = "10")
                                          @Positive Integer size) {
        log.info("GET / event {}", eventId);
        return requestService.getAllByEvent(userId, eventId, from, size);
    }
}