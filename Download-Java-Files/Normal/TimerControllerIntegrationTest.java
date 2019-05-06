package com.clocker.clocker.controller;


import com.clocker.clocker.model.Timer;
import com.clocker.clocker.repository.TimerRepository;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TimerControllerIntegrationTest {

    private TimerController timerController;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TimerRepository timerRepository;
/*
    @Test
    public void testTimerCreating() {

        timerController.addTimer();

        List<Timer> timer = timerRepository.findAll();
        Assertions.assertThat(timer)
                .isNotEmpty()
                .doesNotContainNull();
    }

    @Test
    public void testTimerData() {

        Timer timer = new Timer();
        entityManager.persist(timer);
        entityManager.flush();

        timerController.timerData()
        Assertions.assertThat(timer)
                .isNotEmpty()
                .doesNotContainNull();
    }*/
}
