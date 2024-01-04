package com.example.workcalendar.user.repository;

import com.example.workcalendar.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}