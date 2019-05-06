package com.clocker.clocker.repository;

import com.clocker.clocker.model.Timer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TimerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TimerRepository timerRepository;

    @Test
    public void whenFindById_thenReturnTimer() {
        // given
        Timer timer = new Timer();
        entityManager.persist(timer);
        entityManager.flush();

        // when
        Optional<Timer> found = timerRepository.findById(timer.getId());

        // then
        Assertions.assertThat(found.get().getTitle())
                .isEqualTo(timer.getTitle());

        Assertions.assertThat(found.get().getTitle())
                .startsWith("New")
                .endsWith("Timer")
                .isEqualToIgnoringCase("new timer");
    }
}