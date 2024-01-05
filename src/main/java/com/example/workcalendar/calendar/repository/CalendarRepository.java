package com.example.workcalendar.calendar.repository;

import com.example.workcalendar.calendar.model.Day;
import com.example.workcalendar.calendar.dto.MonthWorkDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface CalendarRepository extends JpaRepository<Day, LocalDate> {
    @Query("SELECT new com.example.workcalendar.calendar.dto.MonthWorkDays(COUNT(d.date)) " +
            "FROM Day AS d " +
            "WHERE EXTRACT(MONTH FROM d.date) = ?1 AND " +
            "EXTRACT(YEAR FROM d.date) = ?2 AND " +
            "d.status = 'WORKDAY'")
    MonthWorkDays findWorkdaysInOneMonth(Integer month, Integer year);
}
