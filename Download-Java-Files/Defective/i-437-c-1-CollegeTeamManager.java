package uk.co.scottlogic.gradProject.server.repos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.scottlogic.gradProject.server.misc.Enums;
import uk.co.scottlogic.gradProject.server.repos.documents.CollegeTeam;
import uk.co.scottlogic.gradProject.server.repos.documents.Player;
import uk.co.scottlogic.gradProject.server.routers.dto.CollegeTeamDTO;
import uk.co.scottlogic.gradProject.server.routers.dto.CollegeTeamStatsDTO;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CollegeTeamManager {

    private static final Logger log = LoggerFactory.getLogger(CollegeTeamManager.class);
    private CollegeTeamRepo teamRepo;
    private PlayerRepo playerRepo;

    @Autowired
    public CollegeTeamManager(CollegeTeamRepo teamRepo, PlayerRepo playerRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
    }


    public CollegeTeamDTO makeTeam(String name) {
        Optional<CollegeTeam> collegeTeam = teamRepo.findByName(name);
        if (collegeTeam.isPresent()){
            throw new IllegalArgumentException("A college team already exists with name : " + name);
        }
        CollegeTeam team = new CollegeTeam(name);
        teamRepo.save(team);
        return new CollegeTeamDTO(team);
    }

    public void deleteTeam(String name) {
        Optional<CollegeTeam> collegeTeam = teamRepo.findByName(name);

        if (collegeTeam.isPresent()) {
            List<Player> players = playerRepo.findByCollegeTeam(collegeTeam.get());

            if (!players.isEmpty()) {
                log.debug("Can't delete a college team that has players associated with it");
                throw new IllegalArgumentException("Can't delete a college team that has players associated with it");
            }
            teamRepo.delete(collegeTeam.get());
        } else {
            log.debug("No college team exists by name ({})", name);
            throw new IllegalArgumentException("No college team with that name exists");
        }
    }

    public void addStatsToCollegeTeam(CollegeTeamStatsDTO dto) {
        Optional<CollegeTeam> collegeTeam = teamRepo.findByName(dto.getCollegeName());
        if (collegeTeam.isPresent()) {
            collegeTeam.get().addGoalsFor(dto.getGoalsFor());
            collegeTeam.get().addGoalsAgainst(dto.getGoalsAgainst());

            if (dto.getResult().equals(Enums.COLLEGE_MATCH_RESULT.WIN)) {
                collegeTeam.get().addWin();
            } else if (dto.getResult().equals(Enums.COLLEGE_MATCH_RESULT.DRAW)) {
                collegeTeam.get().addDraw();
            } else if (dto.getResult().equals(Enums.COLLEGE_MATCH_RESULT.LOSS)) {
                collegeTeam.get().addLoss();
            }

            teamRepo.save(collegeTeam.get());
        } else {
            log.debug("No college team exists by name ({})", dto.getCollegeName());
            throw new IllegalArgumentException("College team does not exist");
        }
    }

    public void editCollegeTeamStats(CollegeTeamStatsDTO dto) {
        Optional<CollegeTeam> collegeTeam = teamRepo.findByName(dto.getCollegeName());
        if (collegeTeam.isPresent()) {
            collegeTeam.get().setGoalsFor(dto.getGoalsFor());
            collegeTeam.get().setGoalsAgainst(dto.getGoalsAgainst());

            collegeTeam.get().setWins(dto.getWins());
            collegeTeam.get().setDraws(dto.getDraws());
            collegeTeam.get().setLosses(dto.getLosses());

            teamRepo.save(collegeTeam.get());
        } else {
            log.debug("No college team exists by name ({})", dto.getCollegeName());
            throw new IllegalArgumentException("College team does not exist");
        }
    }

    // Sorts by points first
    // Then goal difference
    // Then goals for
    // Then reverse alphabetically
    private int compareByPoints(CollegeTeamDTO teamOne, CollegeTeamDTO teamTwo) {
        if (teamOne.getTotalScore() > teamTwo.getTotalScore()) {
            return -1;
        } else if (teamOne.getTotalScore() < teamTwo.getTotalScore()) {
            return 1;
        } else if (teamOne.getGoalsFor() - teamOne.getGoalsAgainst() > teamTwo.getGoalsFor() - teamTwo.getGoalsAgainst()) {
            return -1;
        } else if (teamOne.getGoalsFor() - teamOne.getGoalsAgainst() < teamTwo.getGoalsFor() - teamTwo.getGoalsAgainst()) {
            return 1;
        } else if (teamOne.getGoalsFor() > teamTwo.getGoalsFor()) {
            return -1;
        } else if (teamOne.getGoalsFor() < teamTwo.getGoalsFor()) {
            return 1;
        } else if (teamOne.getName().compareTo(teamTwo.getName()) > 0) {
            return -1;
        } else {
            return 1;
        }
    }

    // Sorts by points first
    // Then goal difference
    // Then goals for
    // Then reverse alphabetically
    private int compareByAlphabet(CollegeTeamDTO teamOne, CollegeTeamDTO teamTwo) {
        if (teamOne.getName().compareTo(teamTwo.getName()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public List<CollegeTeamDTO> getAllCollegeTeams(String sortBy) {
        Iterable<CollegeTeam> teams = teamRepo.findAll();
        List<CollegeTeam> allCollegeTeams = new ArrayList<>();
        teams.forEach(allCollegeTeams::add);
        List<CollegeTeamDTO> teamsOrderedByPoints = new ArrayList<>();
        for (CollegeTeam ct : allCollegeTeams) {
            teamsOrderedByPoints.add(new CollegeTeamDTO(ct));
        }

        if (sortBy.equals("points")) {
            teamsOrderedByPoints.sort(this::compareByPoints);
        } else {
            teamsOrderedByPoints.sort(this::compareByAlphabet);
        }

        return teamsOrderedByPoints;
    }
}
