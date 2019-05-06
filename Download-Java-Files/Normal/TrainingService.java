package at.jugger.tracker.service;

import at.jugger.tracker.dto.Training;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    List<Training> getTrainings();

    @NotNull Training getTraining(@NotNull LocalDate date);

    @NotNull Training createTraining(@NotNull Training training);

    @NotNull Training updateTraining(@NotNull LocalDate date, @NotNull Training training);

    void deleteTraining(@NotNull LocalDate date);
}
