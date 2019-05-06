package at.jugger.tracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("logs")
@Slf4j
public class LogController {

    @Value("${logging.file}")
    private String logFileName;

    public LogController() {
    }

    @GetMapping(value = "/", produces = "text/plain")
    public ResponseEntity<String> getLogs() {
        try {
            String logs = new String(Files.readAllBytes(Paths.get(logFileName)));
            return ResponseEntity.ok(logs);
        } catch (IOException e) {
            log.error("Could not read file " + logFileName, e);
            return ResponseEntity.status(500).build();
        }
    }
}
