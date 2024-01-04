package com.example.workcalendar.request.service;

import com.example.workcalendar.event.model.Event;
import com.example.workcalendar.event.repository.EventRepository;
import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.exception.ParticipantIsInitiatorException;
import com.example.workcalendar.exception.RequestAlreadyExistsException;
import com.example.workcalendar.request.model.Request;
import com.example.workcalendar.request.dto.RequestDto;
import com.example.workcalendar.request.model.RequestMapper;
import com.example.workcalendar.request.repository.RequestRepository;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.workcalendar.request.model.RequestStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public RequestDto addRequest(Long initiatorId, Long eventId, Long participantId) {
        log.info("Создание нового запроса initiator {} event {} participant {}", initiatorId, eventId, participantId);
        Event event = getEvent(eventId);
        User initiator = getUser(initiatorId);
        User participant = getUser(participantId);
        if (requestRepository.existsByInitiatorIdAndEventId(initiatorId, eventId)) {
            throw new RequestAlreadyExistsException("Запрос уже в работе.");
        }
        if (participantId.equals(event.getInitiator().getId())) {
            throw new ParticipantIsInitiatorException("Инициатор события не может присылать себе запросы.");
        }
        Request request = new Request();
        request.setInitiator(initiator);
        request.setEvent(event);
        request.setParticipant(participant);
        request.setStatus(PENDING);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(Long initiatorId, Long requestId) {
        log.info("Отмена запроса c id {} инициатором с id {}", requestId, initiatorId);
        getUser(initiatorId);
        Request request = requestRepository.findByIdAndInitiatorId(requestId, initiatorId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", Request.class)));
        request.setStatus(CANCELED);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public RequestDto confirmRequest(Long participantId, Long requestId) {
        log.info("Подтверждение запроса c id {} участником с id {}", requestId, participantId);
        getUser(participantId);
        Request request = getRequest(requestId, participantId);
        request.setStatus(CONFIRMED);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public RequestDto rejectRequest(Long participantId, Long requestId) {
        log.info("Отклонение запроса c id {} участником с id {}", requestId, participantId);
        getUser(participantId);
        Request request = getRequest(requestId, participantId);
        request.setStatus(REJECTED);
        return RequestMapper.toRequestDto(request);
    }

    public List<RequestDto> getAllByParticipant(Long participantId, Integer from, Integer size) {
        log.info("Получение списка всех запросов участника с id {}", participantId);
        return requestRepository.findAllByParticipantId(participantId, PageRequest.of(from / size, size)).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public List<RequestDto> getAllByEvent(Long userId, Long eventId, Integer from, Integer size) {
        log.info("Получение списка запросов события с id {}", eventId);
        getUser(userId);
        return requestRepository.findAllByEventId(eventId, PageRequest.of(from / size, size)).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", Event.class)));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", User.class)));
    }

    private Request getRequest(Long requestId, Long participantId) {
        return requestRepository.findByIdAndParticipantId(requestId, participantId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Объект класса %s не найден", Request.class)));
    }
}