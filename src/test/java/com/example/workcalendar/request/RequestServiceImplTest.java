package com.example.workcalendar.request;

import com.example.workcalendar.event.model.Event;
import com.example.workcalendar.event.repository.EventRepository;
import com.example.workcalendar.event.dto.EventShortDto;
import com.example.workcalendar.exception.EntityNotFoundException;
import com.example.workcalendar.exception.ParticipantIsInitiatorException;
import com.example.workcalendar.exception.RequestAlreadyExistsException;
import com.example.workcalendar.request.dto.RequestDto;
import com.example.workcalendar.request.model.Request;
import com.example.workcalendar.request.model.RequestStatus;
import com.example.workcalendar.request.repository.RequestRepository;
import com.example.workcalendar.request.service.RequestServiceImpl;
import com.example.workcalendar.user.model.User;
import com.example.workcalendar.user.repository.UserRepository;
import com.example.workcalendar.user.dto.UserShortDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static com.example.workcalendar.request.model.RequestStatus.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private RequestServiceImpl requestService;

    private final Long id = 1L;
    private final User initiator = new User(
            id,
            "Ivanov",
            "Ivan",
            "user@mail.ru",
            "admin",
            "IT");
    private final User participant = new User(
            2L,
            "Petrov",
            "Petr",
            "user2@mail.ru",
            "dev",
            "IT");
    private final UserShortDto userShortDto = new UserShortDto(2L, "Petrov Petr");
    private final Event event = new Event(
            id,
            "consultation",
            LocalDateTime.of(2024, 1, 25, 13, 0, 0),
            LocalDateTime.of(2024, 1, 25, 14, 0, 0),
            "office 25",
            "consultation about Spring Framework",
            initiator);
    private final EventShortDto eventShortDto = new EventShortDto(id, "consultation",
            LocalDateTime.of(2024, 1, 25, 13, 0, 0));
    private final Request request = new Request(id, initiator, event, participant, RequestStatus.PENDING);
    private final RequestDto requestDto = new RequestDto(id, userShortDto, eventShortDto, RequestStatus.PENDING);

    @Test
    void addRequest_whenFieldsValid_thenSavedEvent() {
        when(userRepository.findById(id)).thenReturn(Optional.of(initiator));
        when(userRepository.findById(2L)).thenReturn(Optional.of(participant));
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(requestRepository.save(any())).thenReturn(request);

        RequestDto actualRequest = requestService.addRequest(id, id, 2L);

        Assertions.assertEquals(requestDto, actualRequest);
    }

    @Test
    void addRequest_whenRequestExist_thenThrownException() {
        when(userRepository.findById(id)).thenReturn(Optional.of(initiator));
        when(userRepository.findById(2L)).thenReturn(Optional.of(participant));
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(requestRepository.existsByInitiatorIdAndEventId(anyLong(), anyLong())).thenReturn(true);

        Assertions.assertThrows(RequestAlreadyExistsException.class, () ->
                requestService.addRequest(id, id, 2L));
    }

    @Test
    void addRequest_whenParticipantIsInitiator_thenThrownException() {
        when(userRepository.findById(id)).thenReturn(Optional.of(initiator));
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        Assertions.assertThrows(ParticipantIsInitiatorException.class, () -> requestService.addRequest(id, id, id));
    }

    @Test
    void cancelRequest_whenRequestFound_thenRequestCanceled() {
        when(userRepository.findById(id)).thenReturn(Optional.of(initiator));
        when(requestRepository.findByIdAndInitiatorId(id, id)).thenReturn(Optional.of(request));
        requestDto.setStatus(RequestStatus.CANCELED);

        RequestDto actualRequest = requestService.cancelRequest(id, id);

        Assertions.assertEquals(requestDto, actualRequest);
    }

    @Test
    void cancelRequest_whenRequestNotFound_thenThrownException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(initiator));
        when(requestRepository.findByIdAndInitiatorId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                requestService.cancelRequest(2L, id));
    }

    @Test
    void confirmRequest_whenRequestFound_thenRequestConfirmed() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(participant));
        when(requestRepository.findByIdAndParticipantId(id, 2L)).thenReturn(Optional.of(request));
        requestDto.setStatus(CONFIRMED);

        RequestDto actualRequest = requestService.confirmRequest(2L, id);

        Assertions.assertEquals(requestDto, actualRequest);
    }

    @Test
    void confirmRequest_whenRequestNotFound_thenThrownException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(participant));
        when(requestRepository.findByIdAndParticipantId(anyLong(), anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                requestService.confirmRequest(2L, id));
    }

    @Test
    void rejectRequest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(participant));
        when(requestRepository.findByIdAndParticipantId(id, 2L)).thenReturn(Optional.of(request));
        requestDto.setStatus(REJECTED);

        RequestDto actualRequest = requestService.rejectRequest(2L, id);

        Assertions.assertEquals(requestDto, actualRequest);
    }

    @Test
    void getAllByParticipant() {
        when(requestRepository.findAllByParticipantId(anyLong(), any())).thenReturn(List.of(request));

        List<RequestDto> targetRequests = requestService.getAllByParticipant(2L, 0, 10);

        Assertions.assertNotNull(targetRequests);
        Assertions.assertEquals(1, targetRequests.size());
        verify(requestRepository, times(1))
                .findAllByParticipantId(anyLong(), any());
    }

    @Test
    void getAllByEvent() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(participant));
        when(requestRepository.findAllByEventId(anyLong(), any())).thenReturn(List.of(request));

        List<RequestDto> targetRequests = requestService.getAllByEvent(id, id, 0, 10);

        Assertions.assertNotNull(targetRequests);
        Assertions.assertEquals(1, targetRequests.size());
        verify(requestRepository, times(1))
                .findAllByEventId(anyLong(), any());
    }
}