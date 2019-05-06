package at.jugger.tracker.controller;

import at.jugger.tracker.api.TrainingApiDelegate;
import at.jugger.tracker.dto.Training;
import at.jugger.tracker.exceptions.TrainingNotFoundException;
import at.jugger.tracker.service.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TrainingController implements TrainingApiDelegate {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Override
    public ResponseEntity<Training> createTraining(@NotNull Training training) {
        return ResponseEntity.ok(trainingService.createTraining(training));
    }

    @Override
    public ResponseEntity<Void> deleteTraining(@NotNull LocalDate date) {
        trainingService.deleteTraining(date);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Training> getTraining(@NotNull LocalDate date) {
        Training training = trainingService.getTraining(date);

        if (training == null) {
            throw new TrainingNotFoundException("date", date.toString());
        }

        return ResponseEntity.ok(training);
    }

    @Override
    public ResponseEntity<List<Training>> getTrainings() {
        return ResponseEntity.ok(trainingService.getTrainings());
    }

    @Override
    public ResponseEntity<Training> updateTraining(@NotNull LocalDate date, @NotNull Training training) {
        return ResponseEntity.ok(trainingService.updateTraining(date, training));
    }
}
