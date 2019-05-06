package com.clocker.clocker.controller;

import com.clocker.clocker.payload.*;
import com.clocker.clocker.repository.TimerRepository;
import com.clocker.clocker.repository.UserRepository;
import com.clocker.clocker.exception.ResourceNotFoundException;
import com.clocker.clocker.model.Timer;
import com.clocker.clocker.model.User;
import com.clocker.clocker.security.CurrentUser;
import com.clocker.clocker.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/api/timer")
public class TimerController {

    @Autowired
    private TimerRepository timerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/add")
    public ResponseEntity<?> addTimer(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));


        user.endPreviousTimers();
        // Create timer
        Timer timer = new Timer();
        timerRepository.save(timer);

        //Add timer to current user
        Set<Timer> userTimers = user.getTimers();
        userTimers.add(timer);
        user.setTimers(userTimers);
        userRepository.save(user);

        return new ResponseEntity(new TimerCreateResponse(true, "Timer create success!", new Date()),
                HttpStatus.OK);
    }

    @GetMapping("/timers")
    public UserTimers getUserTimers(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        UserTimers userTimers = new UserTimers(user.getTimers());

        return userTimers;
    }

    @PostMapping("/setTitle/{timerId}")
    public ResponseEntity<?> updateTitleTimer(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "timerId") Long timerId, @RequestBody TimerUpdateRequest timerUpdateRequest) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer", "id", timerId));
        timer.setTitle(timerUpdateRequest.getTitle());
        timerRepository.save(timer);


        return new ResponseEntity(new ApiResponse(true, "Timer update success!"),
                HttpStatus.OK);
    }

    @PostMapping("/stop/{timerId}")
    public ResponseEntity<?> stopTimer(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "timerId") Long timerId) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer", "id", timerId));
        timer.stopTimer();
        timerRepository.save(timer);

        return new ResponseEntity(new ApiResponse(true, "Timer stop success!"),
                HttpStatus.OK);
    }

    @PostMapping("/resume/{timerId}")
    public ResponseEntity<?> resumeTimer(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "timerId") Long timerId) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        user.endPreviousTimers();
        Timer oldTimer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer", "id", timerId));

        Timer newTimer = new Timer(oldTimer.getTitle());
        timerRepository.save(newTimer);

        Set<Timer> userTimers = user.getTimers();
        userTimers.add(newTimer);
        user.setTimers(userTimers);
        userRepository.save(user);

        return new ResponseEntity(new TimerCreateResponse(true, "Timer resume success!", new Date()),
                HttpStatus.OK);
    }

    @GetMapping("/timer/{timerId}")
    public TimerResponse timerData(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "timerId") Long timerId) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer", "id", timerId));

        return new TimerResponse(timer.getId(), timer.getStart_at(), timer.getEnd_at(), timer.getTitle());
    }

    @PostMapping("/update/time/{timerId}")
    public ResponseEntity<?> timerUpdateTime(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "timerId") Long timerId, @RequestBody TimerUpdateEndTimeRequest timerUpdateEndTimeRequest) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer", "id", timerId));
        timer.stopTimer();
        timer.setStart_at(timerUpdateEndTimeRequest.getStart_time());
        timer.setEnd_at(timerUpdateEndTimeRequest.getEnd_time());
        timerRepository.save(timer);

        return new ResponseEntity(new ApiResponse(true, "Timer update success!"),
                HttpStatus.OK);
    }

    @PostMapping("/stopAll")
    public ResponseEntity<?> stopAll(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        user.endPreviousTimers();

        return new ResponseEntity(new ApiResponse(true, "All timers stopped successfully!"),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{timerId}")
    public ResponseEntity<?> deleteTimer(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "timerId") Long timerId) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer", "id", timerId));
        timer.stopTimer();
        timerRepository.delete(timer);

        Set<Timer> userTimers = user.getTimers();
        userTimers.remove(timer);
        user.setTimers(userTimers);
        userRepository.save(user);

        return new ResponseEntity(new ApiResponse(true, "Timer delete success!"),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteTimer(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));

        timerRepository.deleteAll();

        Set<Timer> userTimers = user.getTimers();
        userTimers.clear();
        user.setTimers(userTimers);
        userRepository.save(user);

        return new ResponseEntity(new ApiResponse(true, "Timer delete success!"),
                HttpStatus.OK);
    }
}
