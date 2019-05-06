package com.clocker.clocker.repository;

import com.clocker.clocker.model.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    Optional<Timer> findByTitle(String title);

    List<Timer> findByIdIn(List<Long> timerIds);

    Optional<Timer> findById(Long timerId);
}