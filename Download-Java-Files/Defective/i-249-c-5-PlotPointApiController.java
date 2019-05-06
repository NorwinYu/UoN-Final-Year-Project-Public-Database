package org.kaccag.plotpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Any errors thrown should be directed to the error controller.
 */
@RestController
public class PlotPointApiController {

    private static final Logger LOGGER = Logger.getLogger(
            PlotPointApiController.class.getSimpleName());

    private static final String BASE_PATH = "/api/plotpoint";

    @Autowired
    private PlotPointService service;

    public PlotPointApiController(final PlotPointService service) {
        this.service = service;
    }

    @GetMapping(
            value = BASE_PATH + "/help",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointHelp> getHelp() {
        LOGGER.info("Request received to get help details");
        PlotPointHelp helper = new PlotPointHelp();
        return new ResponseEntity<>(helper, HttpStatus.OK);
    }

    @PostMapping(
            value = BASE_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> create(@RequestBody final PlotPointEntity plotPoint) {
        LOGGER.info("Request received to create new plot point");
        PlotPointEntity inserted = service.insert(plotPoint);
        return new ResponseEntity<>(inserted, HttpStatus.CREATED);
    }

    @PatchMapping(
            value = BASE_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> update(@RequestBody final PlotPointEntity newPlotPoint) {
        LOGGER.info("Request received to update plot point");
        PlotPointEntity updatedValue = service.update(newPlotPoint);
        return new ResponseEntity<>(updatedValue, HttpStatus.OK);
    }

    @DeleteMapping(
            value = BASE_PATH + "/id/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> delete(@PathVariable final UUID id) {
        LOGGER.info("Request received to delete plot point");
        PlotPointEntity deleted = service.delete(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    /**
     * Expected that a calling client will perform calculation
     * for generation of id based on user and summary.
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = BASE_PATH + "/id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PlotPointEntity> findOne(@PathVariable final UUID id) {
        LOGGER.info(String.format("Request received to get plot point of id '%s'", id.toString()));
        PlotPointEntity returned = service.getById(id);
        return new ResponseEntity<>(returned, HttpStatus.OK);
    }

    /**
     * Searching for all plot points that are from a single user.
     * User may be a token or similar in the future.
     *
     * @param user
     * @return
     */
    @GetMapping(
            value = BASE_PATH + "/user/{user}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<PlotPointEntity>> findAllByUser(@PathVariable final String user) {
        LOGGER.info(String.format("Request received to get all plot point by user '%s.'", user));
        List<PlotPointEntity> allByUser = service.getByUser(user);
        return new ResponseEntity<>(allByUser, HttpStatus.OK);
    }

    @GetMapping(
            value = BASE_PATH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<PlotPointEntity>> findAll() {
        LOGGER.info("Request received to get all plot points");
        List<PlotPointEntity> allPlotPoints = service.getAll();
        return new ResponseEntity<>(allPlotPoints, HttpStatus.OK);
    }
}
