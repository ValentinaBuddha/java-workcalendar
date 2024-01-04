package com.example.workcalendar.request.service;

import com.example.workcalendar.request.dto.RequestDto;

import java.util.List;

public interface RequestService {

    RequestDto addRequest(Long initiatorId, Long eventId, Long participantId);

    RequestDto cancelRequest(Long initiatorId, Long requestId);

    RequestDto confirmRequest(Long participantId, Long requestId);

    RequestDto rejectRequest(Long participantId, Long requestId);

    List<RequestDto> getAllByParticipant(Long participantId, Integer from, Integer size);

    List<RequestDto> getAllByEvent(Long userId, Long eventId, Integer from, Integer size);
}
