package com.example.workcalendar.request.repository;

import com.example.workcalendar.request.model.Request;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Boolean existsByInitiatorIdAndEventId(Long userId, Long eventId);

    Optional<Request> findByIdAndInitiatorId(Long requestId, Long initiatorId);

    Optional<Request> findByIdAndParticipantId(Long requestId, Long participantId);

    List<Request> findAllByParticipantId(Long participantId, Pageable pageable);

    List<Request> findAllByEventId(Long eventId, Pageable pageable);
}
