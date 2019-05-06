package uk.co.scottlogic.gradProject.server.repos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.scottlogic.gradProject.server.misc.Constants;
import uk.co.scottlogic.gradProject.server.misc.Enums;
import uk.co.scottlogic.gradProject.server.repos.documents.*;
import uk.co.scottlogic.gradProject.server.routers.dto.AddMultiplePointsDTO;
import uk.co.scottlogic.gradProject.server.routers.dto.MakePlayerDTO;
import uk.co.scottlogic.gradProject.server.routers.dto.PlayerPointsDTO;
import uk.co.scottlogic.gradProject.server.routers.dto.SubmitPointsDTO;

import java.util.*;

@Service
public class PlayerManager {

    private static final Logger log = LoggerFactory.getLogger(PlayerManager.class);

    private CollegeTeamRepo teamRepo;

    private PlayerRepo playerRepo;

    private PlayerPointsRepo playerPointsRepo;

    private WeeklyTeamRepo weeklyTeamRepo;

    private ApplicationUserRepo applicationUserRepo;

    @Autowired
    public PlayerManager(CollegeTeamRepo teamRepo, PlayerRepo playerRepo, PlayerPointsRepo playerPointsRepo, WeeklyTeamRepo weeklyTeamRepo, ApplicationUserRepo applicationUserRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
        this.playerPointsRepo = playerPointsRepo;
        this.weeklyTeamRepo = weeklyTeamRepo;
        this.applicationUserRepo = applicationUserRepo;

//        CollegeTeam collegeTeam = new CollegeTeam("A");
//        teamRepo.save(collegeTeam);
//
//        Player p = new Player(collegeTeam, Enums.Position.GOALKEEPER, 2, "fn1" ,"sn1");
//
//        Player p1 = new Player(collegeTeam, Enums.Position.DEFENDER, 2, "fn2" ,"sn2");
//        Player p2 = new Player(collegeTeam, Enums.Position.DEFENDER, 2, "fn3" ,"sn3");
//        Player p3 = new Player(collegeTeam, Enums.Position.DEFENDER, 2, "fn4" ,"sn4");
//        Player p4 = new Player(collegeTeam, Enums.Position.DEFENDER, 2, "fn5" ,"sn5");
//
//        Player p5 = new Player(collegeTeam, Enums.Position.MIDFIELDER, 2, "fn6" ,"sn6");
//        Player p6 = new Player(collegeTeam, Enums.Position.MIDFIELDER, 2, "fn7" ,"sn7");
//        Player p7 = new Player(collegeTeam, Enums.Position.MIDFIELDER, 2, "fn8" ,"sn8");
//        Player p8 = new Player(collegeTeam, Enums.Position.MIDFIELDER, 2, "fn9" ,"sn9");
//
//        Player p9 = new Player(collegeTeam, Enums.Position.ATTACKER, 2, "fn10" ,"sn10");
//        Player p10 = new Player(collegeTeam, Enums.Position.ATTACKER, 2, "fn11" ,"sn11");
//
//        playerRepo.save(p);
//        playerRepo.save(p1);
//        playerRepo.save(p2);
//        playerRepo.save(p3);
//        playerRepo.save(p4);
//        playerRepo.save(p5);
//        playerRepo.save(p6);
//        playerRepo.save(p7);
//        playerRepo.save(p8);
//        playerRepo.save(p9);
//        playerRepo.save(p10);

    }

    public void makePlayer(MakePlayerDTO makePlayerDTO) {
        Optional<CollegeTeam> team = teamRepo.findByName(makePlayerDTO.getCollegeTeam());
        if (team.isPresent()) {
            Player player = new Player(team.get(), makePlayerDTO.getPosition(), makePlayerDTO.getPrice(), makePlayerDTO.getFirstName(), makePlayerDTO.getSurname());
            playerRepo.save(player);
        } else {
            log.debug("Invalid team");
            throw new IllegalArgumentException("Invalid team");
        }
    }

    public void deletePlayer(String playerID) {
        Optional<Player> player = playerRepo.findById(UUID.fromString(playerID));
        if (player.isPresent()) {
            if (playerExistsInWeeklyTeam(player.get())) {
                log.debug("Player ({}) is already in a team", player.get().getFirstName());
                throw new IllegalArgumentException("That player is in a weekly team - can't delete them");
            } else {
                playerRepo.delete(player.get());
                log.debug("Deleted player ({})", player.get().getFirstName());
            }
        } else {
            log.debug("Cannot delete player - player does not exist");
            throw new IllegalArgumentException("Player does not exist");
        }
    }

    private boolean playerExistsInWeeklyTeam(Player player) {
        Iterable<UsersWeeklyTeam> usersWeeklyTeams = weeklyTeamRepo.findAll();
        List<UsersWeeklyTeam> allWeeklyTeams = new ArrayList<>();
        usersWeeklyTeams.forEach(allWeeklyTeams::add);
        for (UsersWeeklyTeam uwt : allWeeklyTeams) {
            List<Player> players = uwt.getPlayers();
            for (Player p : players) {
                if (p.getId().equals(player.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void makeNewWeek(Integer newWeek){
        Iterable<ApplicationUser> allUsers = applicationUserRepo.findAll();
        List<ApplicationUser> users = new ArrayList<>();
        allUsers.forEach(users::add);
        for (ApplicationUser user : users){
            List<UsersWeeklyTeam> weeklyTeams = weeklyTeamRepo.findMostRecentWeeklyTeam(user);
            if (weeklyTeams.isEmpty()){
                System.out.println("user has no weekly teams");
            }
            else {
                UsersWeeklyTeam mostRecent = weeklyTeams.get(0);
                UsersWeeklyTeam newUWT = new UsersWeeklyTeam(user, new Date(), mostRecent.getPlayers(), newWeek);
                weeklyTeamRepo.save(newUWT);
            }
        }
    }

    // TO:DO - Change max week per team
    public void submitResults(SubmitPointsDTO pointsDTO){

        Integer maxWeek = weeklyTeamRepo.findNumberOfWeeks();
        if (pointsDTO.getWeek() == maxWeek+1){
            makeNewWeek(pointsDTO.getWeek());
        }
        else if (pointsDTO.getWeek() > maxWeek+1){
            throw new IllegalArgumentException("Missing out a week of points. The next week should");
        }

        System.out.println("submitting results");
        Optional<CollegeTeam> collegeTeam = teamRepo.findByName(pointsDTO.getTeamName());
        if (collegeTeam.isPresent()){

            if (pointsDTO.getGoalsFor().equals(pointsDTO.getGoalsAgainst())){
                System.out.println("added a college draw");
                collegeTeam.get().addDraw();
            }
            else if (pointsDTO.getGoalsFor() > pointsDTO.getGoalsAgainst()){
                System.out.println("added a college win");
                collegeTeam.get().addWin();
            }
            else {
                System.out.println("added a college loss");
                collegeTeam.get().addLoss();
            }
            System.out.println("added " + pointsDTO.getGoalsFor() + " goals for");
            collegeTeam.get().addGoalsFor(pointsDTO.getGoalsFor());

            System.out.println("added " + pointsDTO.getGoalsAgainst() + " goals against");
            collegeTeam.get().addGoalsAgainst(pointsDTO.getGoalsAgainst());

            for (String goalScorerID : pointsDTO.getGoalScorers()){
                Optional<Player> player = playerRepo.findById(UUID.fromString(goalScorerID));
                if (player.isPresent()){
                    System.out.println("adding a goal to " + player.get().getFirstName() + "  " + player.get().getSurname());
                    addGoalToPlayer(player.get(), pointsDTO.getWeek());
                }
                else {
                    throw new IllegalArgumentException("No player exists for player id " + goalScorerID);
                }
            }

            for (String assistsID : pointsDTO.getAssists()){
                Optional<Player> player = playerRepo.findById(UUID.fromString(assistsID));
                if (player.isPresent()){
                    System.out.println("adding an assist to " + player.get().getFirstName() + "  " + player.get().getSurname());
                    addAssistToPlayer(player.get(), pointsDTO.getWeek());
                }
                else {
                    throw new IllegalArgumentException("No player exists for player id " + assistsID);
                }
            }

            for (String cleanSheetID : pointsDTO.getCleanSheets()){
                Optional<Player> player = playerRepo.findById(UUID.fromString(cleanSheetID));
                if (player.isPresent()){
                    System.out.println("adding a clean sheet to " + player.get().getFirstName() + "  " + player.get().getSurname());
                    addCleanSheet(player.get(), pointsDTO.getWeek());
                }
                else {
                    throw new IllegalArgumentException("No player exists for player id " + cleanSheetID);
                }
            }
        }
        else {
            throw new IllegalArgumentException("College team ( " + pointsDTO.getTeamName() + " )  does not exist");
        }
    }

    void makePlayer(CollegeTeam activeTeam, Enums.Position position, double price, String firstName, String surname) {
        Optional<CollegeTeam> team = teamRepo.findById(activeTeam.getId());
        if (team.isPresent()) {
            Player player = new Player(team.get(), position, price, firstName, surname);
            playerRepo.save(player);
            log.debug("Made a player named ({})", player.getFirstName());
        } else {
            log.debug("Cannot make a player with that team ID");
            throw new IllegalArgumentException("Invalid Team ID");
        }
    }

    // When adding points to a player
    // Add points to all the weekly teams they belong to for the correct week
    // Update the users total score as well
    void addPointsToPlayer(Player player, Date date, Integer goals, Integer assists, Boolean cleanSheet, Integer minutesPlayed, Integer yellowCards, Boolean redCard, Boolean manOfTheMatch, Integer week) {
        PlayerPoints newPlayerPoints = new PlayerPoints(goals, assists, minutesPlayed, manOfTheMatch, yellowCards, redCard, cleanSheet, date, player, week);
        newPlayerPoints.setPoints(calculateScore(newPlayerPoints));
        playerPointsRepo.save(newPlayerPoints);
        Integer score = calculateScore(newPlayerPoints);

        player.changeScore(score);
        player.changeGoals(goals);
        player.changeAssists(assists);
        playerRepo.save(player);
        // Shouldn't this also filter by week? -> Only update the week that the points are being added to
        List<UsersWeeklyTeam> weeklyTeams = weeklyTeamRepo.findByPlayers(player);
        for (UsersWeeklyTeam uwt : weeklyTeams) {

            if (uwt.getWeek().equals(week)) {
                // Increase the weekly team score
                uwt.changePoints(score);
                weeklyTeamRepo.save(uwt);

                // Increase the users total score
                ApplicationUser user = uwt.getUser();
                user.changeTotalPoints(score);
                applicationUserRepo.save(user);
            }
        }
    }

    Integer calculateScore(PlayerPoints playerPoints) {
        System.out.println("calculating the score");
        Integer total = 0;
        Player player = playerPoints.getPlayer();
        if (player.getPosition() == Enums.Position.DEFENDER || player.getPosition() == Enums.Position.GOALKEEPER) {
            total += playerPoints.getNumberOfGoals() * Constants.POINTS_PER_DEFENDER_GOAL;
            if (playerPoints.isCleanSheet()) {
                total += Constants.POINTS_PER_CLEAN_SHEET;
            }
        } else if (player.getPosition() == Enums.Position.MIDFIELDER) {
            total += playerPoints.getNumberOfGoals() * Constants.POINTS_PER_MIDFIELDER_GOAL;
        } else if (player.getPosition() == Enums.Position.ATTACKER) {
            total += playerPoints.getNumberOfGoals() * Constants.POINTS_PER_ATTACKER_GOAL;
        }
        total += playerPoints.getNumberOfAssists() * Constants.POINTS_PER_ASSIST;
        if (playerPoints.getMinutesPlayed() > 60) {
            total += 2;
        } else if (playerPoints.getMinutesPlayed() > 0) {
            total += 1;
        }
        total += playerPoints.getYellowCards() * Constants.POINTS_PER_YELLOW_CARD;
        if (playerPoints.isRedCard()) {
            total += Constants.POINTS_PER_RED_CARD;
        }
        if (playerPoints.isManOfTheMatch()) {
            total += Constants.MAN_OF_THE_MATCH_BONUS;
        }
        System.out.println("calculated a score of " + total);
        return total;
    }

    public List<Player> findPlayersByCollegeTeam(String team) {
        Optional<CollegeTeam> collegeTeam = teamRepo.findByName(team);
        if (collegeTeam.isPresent()) {
            return playerRepo.findByCollegeTeam(collegeTeam.get());
        } else {
            log.debug("Team does not exist");
            throw new IllegalArgumentException("Team does not exist");
        }

    }

    // Doesn't add points to players who already have had points added to them
    public void addPointsToSeveralPlayers(AddMultiplePointsDTO points) {

        for (PlayerPointsDTO dto : points.getPointsToAdd()) {
            Optional<Player> player = playerRepo.findById(UUID.fromString(dto.getPlayerID()));
            if (player.isPresent()) {
                Optional<PlayerPoints> pPoints = playerPointsRepo.findByPlayerByWeek(player.get(), dto.getWeek());
                if (pPoints.isPresent()) {
                    log.debug("Player ({}) already has points associated for week ({})", player.get().getFirstName(), dto.getWeek());
                    throw new IllegalArgumentException("Player " + player.get().getFirstName() + " already has points assigned for week " + dto.getWeek());
                }
                PlayerPoints playerPoints = new PlayerPoints(dto, player.get());
                addPointsToPlayer(playerPoints);
            } else {
                log.debug("Player does not exist");
                throw new IllegalArgumentException("Player does not exist");
            }
        }
    }

    public void addPointsToSinglePlayer(PlayerPointsDTO dto) {

        Optional<Player> player = playerRepo.findById(UUID.fromString(dto.getPlayerID()));
        if (player.isPresent()) {

            Optional<PlayerPoints> playerPoints = playerPointsRepo.findByPlayerByWeek(player.get(), dto.getWeek());
            if (playerPoints.isPresent()) {
                log.debug("Player ({}) already has points associated for week ({})", player.get().getFirstName(), dto.getWeek());
                throw new IllegalArgumentException("Player " + player.get().getFirstName() + " already has points assigned for week " + dto.getWeek());
            }
            PlayerPoints pPoints = new PlayerPoints(dto, player.get());
            addPointsToPlayer(pPoints);
        } else {
            log.debug("Player does not exist");
            throw new IllegalArgumentException("Player does not exist");
        }
    }

    private void addGoalToPlayer(Player player, Integer week){
        Optional<PlayerPoints> playerPoints = playerPointsRepo.findByPlayerByWeek(player, week);
        player.changeGoals(1);
        playerRepo.save(player);
        System.out.println("gave them a goal");


        if (playerPoints.isPresent()){
            playerPoints.get().addGoal();
            playerPoints.get().setPoints(calculateScore(playerPoints.get()));
            playerPointsRepo.save(playerPoints.get());
            List<UsersWeeklyTeam> weeklyTeams = weeklyTeamRepo.findByPlayers(playerPoints.get().getPlayer());
            for (UsersWeeklyTeam uwt : weeklyTeams) {
                ApplicationUser user = uwt.getUser();
                if (player.getPosition().equals(Enums.Position.ATTACKER)) {
                    uwt.changePoints(Constants.POINTS_PER_ATTACKER_GOAL);
                    user.changeTotalPoints(Constants.POINTS_PER_ATTACKER_GOAL);
                }
                else if (player.getPosition().equals(Enums.Position.MIDFIELDER)){
                    uwt.changePoints(Constants.POINTS_PER_MIDFIELDER_GOAL);
                    user.changeTotalPoints(Constants.POINTS_PER_MIDFIELDER_GOAL);
                }
                else {
                    uwt.changePoints(Constants.POINTS_PER_DEFENDER_GOAL);
                    user.changeTotalPoints(Constants.POINTS_PER_DEFENDER_GOAL);
                }
                weeklyTeamRepo.save(uwt);
                applicationUserRepo.save(user);
            }
        }
        else {
            System.out.println("player had no points object - made a new one with 1 goal");
            PlayerPoints playerPoints1 = new PlayerPoints(0, 0, 0, false, 0, false, false, new Date(), player, week);
            playerPointsRepo.save(playerPoints1);
            addGoalToPlayer(player, week);
        }
        System.out.println("updated users and weekly teams with them in");
    }

    private void addAssistToPlayer(Player player, Integer week){
        Optional<PlayerPoints> playerPoints = playerPointsRepo.findByPlayerByWeek(player, week);
        player.changeAssists(1);
        playerRepo.save(player);
        if (playerPoints.isPresent()){
            playerPoints.get().addAssist();
            playerPoints.get().setPoints(calculateScore(playerPoints.get()));
            playerPointsRepo.save(playerPoints.get());

            List<UsersWeeklyTeam> weeklyTeams = weeklyTeamRepo.findByPlayers(playerPoints.get().getPlayer());
            for (UsersWeeklyTeam uwt : weeklyTeams) {
                ApplicationUser user = uwt.getUser();
                uwt.changePoints(Constants.POINTS_PER_ASSIST);
                user.changeTotalPoints(Constants.POINTS_PER_ASSIST);
                weeklyTeamRepo.save(uwt);
                applicationUserRepo.save(user);
            }
        }
        else {
            System.out.println("player had no points object - made a new one with 1 assist");
            PlayerPoints playerPoints1 = new PlayerPoints(0, 0, 0, false, 0, false, false, new Date(), player, week);
            playerPointsRepo.save(playerPoints1);
            addAssistToPlayer(player, week);
        }
    }

    private void addCleanSheet(Player player, Integer week){
        Optional<PlayerPoints> playerPoints = playerPointsRepo.findByPlayerByWeek(player, week);
        if (playerPoints.isPresent()){
            playerPoints.get().setCleanSheet(true);
            playerPoints.get().setPoints(calculateScore(playerPoints.get()));
            playerPointsRepo.save(playerPoints.get());

            List<UsersWeeklyTeam> weeklyTeams = weeklyTeamRepo.findByPlayers(playerPoints.get().getPlayer());
            for (UsersWeeklyTeam uwt : weeklyTeams) {
                ApplicationUser user = uwt.getUser();
                uwt.changePoints(Constants.POINTS_PER_CLEAN_SHEET);
                user.changeTotalPoints(Constants.POINTS_PER_CLEAN_SHEET);
                weeklyTeamRepo.save(uwt);
                applicationUserRepo.save(user);
            }
        }
        else {
            System.out.println("player had no points object - made a new one with a clean sheet");
            PlayerPoints playerPoints1 = new PlayerPoints(0, 0, 0, false, 0, false, false, new Date(), player, week);
            playerPointsRepo.save(playerPoints1);
            addCleanSheet(player, week);
        }
    }

    public void editPoints(PlayerPointsDTO dto) {
        Optional<Player> player = playerRepo.findById(UUID.fromString(dto.getPlayerID()));
        if (player.isPresent()) {
            Optional<PlayerPoints> playerPoints = playerPointsRepo.findByPlayerByWeek(player.get(), dto.getWeek());
            if (playerPoints.isPresent()) {
                Integer goalsBefore = playerPoints.get().getNumberOfGoals();
                Integer goalsAssists = playerPoints.get().getNumberOfAssists();
                Integer previousScore = calculateScore(playerPoints.get());

                PlayerPoints newPlayerPoints = new PlayerPoints(dto, player.get());

                Integer newScore = calculateScore(newPlayerPoints);

                playerPoints.get().setPoints(newScore);
                playerPoints.get().setValues(newPlayerPoints);
                Integer differenceInScores = newScore - previousScore;

                player.get().changeGoals(newPlayerPoints.getNumberOfGoals() - goalsBefore);
                player.get().changeAssists(newPlayerPoints.getNumberOfAssists() - goalsAssists);
                player.get().changeScore(differenceInScores);

                playerPointsRepo.save(playerPoints.get());
                playerRepo.save(player.get());

                List<UsersWeeklyTeam> weeklyTeams = weeklyTeamRepo.findByPlayers(playerPoints.get().getPlayer());
                for (UsersWeeklyTeam uwt : weeklyTeams) {
                    uwt.changePoints(differenceInScores);
                    weeklyTeamRepo.save(uwt);

                    ApplicationUser user = uwt.getUser();
                    user.changeTotalPoints(differenceInScores);
                    applicationUserRepo.save(user);
                }
            } else {
                log.debug("Player ({}) already has points associated for week ({})", player.get().getFirstName(), dto.getWeek());
                throw new IllegalArgumentException("Player " + player.get().getFirstName() + " does not have points assigned for week " + dto.getWeek());
            }
        } else {
            log.debug("Player does not exist");
            throw new IllegalArgumentException("Player does not exist");
        }
    }

    public void addPointsToPlayer(PlayerPoints playerPoints) {
        Integer score = calculateScore(playerPoints);
        playerPoints.setPoints(score);
        playerPointsRepo.save(playerPoints);
        playerPoints.getPlayer().changeScore(score);
        playerPoints.getPlayer().changeGoals(playerPoints.getNumberOfGoals());
        playerPoints.getPlayer().changeAssists(playerPoints.getNumberOfAssists());
        playerRepo.save(playerPoints.getPlayer());
        List<UsersWeeklyTeam> weeklyTeams = weeklyTeamRepo.findByPlayers(playerPoints.getPlayer());
        for (UsersWeeklyTeam uwt : weeklyTeams) {

            if (uwt.getWeek().equals(playerPoints.getWeek())) {
                // Increase the weekly team score
                uwt.changePoints(score);
                weeklyTeamRepo.save(uwt);

                // Increase the users total score
                ApplicationUser user = uwt.getUser();
                user.changeTotalPoints(score);
                applicationUserRepo.save(user);
            }
        }
    }

    public PlayerPoints findStatsForPlayerInWeek(String playerID, Integer week) {
        Optional<Player> player = playerRepo.findById(UUID.fromString(playerID));
        if (player.isPresent()) {
            Optional<PlayerPoints> playerPoints = playerPointsRepo.findByPlayerByWeek(player.get(), week);
            if (playerPoints.isPresent()) {
                return playerPoints.get();
            } else {
                log.debug("Player has no points for week ({})", week);
                throw new IllegalArgumentException("This player does not have any points for that week");
            }
        } else {
            throw new IllegalArgumentException("Player does not exist");
        }
    }

    // Possibly just need this to return 0 if the player doesn't exist
    Integer findPointsForPlayerInWeek(Player player, Integer week) {
        System.out.println("finding points for player " + player.getFirstName() + " in week " + week);
        Optional<PlayerPoints> playerPoints = playerPointsRepo.findByPlayerByWeek(player, week);
        if (playerPoints.isPresent()) {
            System.out.println("they have points");
            return calculateScore(playerPoints.get());
        } else if (week == 0) {
            return 0;
        } else {
            log.debug("Player has no points for that week");
            return 0;
//            throw new IllegalArgumentException("Player doesn't exist");
        }
    }

    public List<PlayerPoints> findPlayerWithMostPointsInWeek(Integer week) {
        return playerPointsRepo.findPlayerWithMostPoints(week);
    }

    // Should be careful here -> could cause some teams to become invalid
    public void changePlayersCollegeTeam(String playerID, String collegeName) {

        Optional<Player> player = playerRepo.findById(UUID.fromString(playerID));
        if (player.isPresent()) {
            Optional<CollegeTeam> collegeTeam = teamRepo.findByName(collegeName);
            if (collegeTeam.isPresent()) {
                player.get().setActiveTeam(collegeTeam.get());
            } else {
                log.debug("College team does not exist");
                throw new IllegalArgumentException("College team does not exist");
            }
        } else {
            log.debug("Player does not exist");
            throw new IllegalArgumentException("Player does not exist");
        }
    }

    public List<Player> formatFilter(String team, Enums.Position position, Integer min, Integer max, String name, Enums.SORT_BY sortBy) {
        System.out.println("received request - name = " + name);
        if (team.equals("All teams")) {
            if (position.equals(Enums.Position.ALL)) {
                return filter(null, null, min, max, name, sortBy);
            } else {
                return filter(null, position.ordinal(), min, max, name, sortBy);
            }
        } else {
            Optional<CollegeTeam> collegeTeam = teamRepo.findByName(team);
            if (collegeTeam.isPresent()) {
                if (position != Enums.Position.ALL) {
                    return filter(collegeTeam.get(), position.ordinal(), min, max, name, sortBy);
                } else {
                    return filter(collegeTeam.get(), null, min, max, name, sortBy);
                }
            } else {
                log.debug("College team ({}) does not exist", team);
                throw new IllegalArgumentException("College team does not exist");
            }
        }
    }

    private List<Player> filter(CollegeTeam team, Integer position, Integer minPrice, Integer maxPrice, String name, Enums.SORT_BY sorting) {
        String searchName = name;
        // Search for everything if the input is null
        if (name.equals("null")) {
            searchName = "%";
        }
        // Now searches for anything that contains 'searchName'
        else {
            searchName = "%" + searchName + "%";
        }
        // Order by price by default
        if (sorting == Enums.SORT_BY.GOALS) {
            return playerRepo.filterPlayersSortByGoals(team, position, minPrice, maxPrice, searchName);
        } else if (sorting == Enums.SORT_BY.ASSISTS) {
            return playerRepo.filterPlayersSortByAssists(team, position, minPrice, maxPrice, searchName);
        } else if (sorting == Enums.SORT_BY.TOTAL_POINTS) {
            return playerRepo.filterPlayersSortByScore(team, position, minPrice, maxPrice, searchName);
        } else {
            return playerRepo.filterPlayersSortByPrice(team, position, minPrice, maxPrice, searchName);
        }
    }

    //
    public void addPointsToPlayersWeek0() {
        Optional<Player> player1 = playerRepo.findByFirstName("John");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 3, 6, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Phil");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 1, 2, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Chris");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 4, 1, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("David");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 2, 2, false, 90, 0, false, false, 0));


        player1 = playerRepo.findByFirstName("Bernado");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 5, 3, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Kevin");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 1, 5, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Paul");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 2, 1, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Paco");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 3, 4, false, 90, 0, false, false, 0));


        player1 = playerRepo.findByFirstName("Marcus");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 4, 8, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Romelu");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 1, 5, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Dom");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 2, 2, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Ed");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 9, 3, false, 90, 0, false, false, 0));


        player1 = playerRepo.findByFirstName("Joe");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 10, 0, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Stevie");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 70, 1, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Ollie");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 21, 3, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Eloka");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 72, 4, false, 90, 0, false, false, 0));


        player1 = playerRepo.findByFirstName("Herbie");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 23, 5, false, 90, 0, false, false, 0));

        player1 = playerRepo.findByFirstName("Eduardo");
        player1.ifPresent(player -> addPointsToPlayer(player, new Date(), 43, 1, false, 90, 0, false, false, 0));

    }

    public void makePlayers() {
        Optional<CollegeTeam> team = teamRepo.findByName("A");
        if (!team.isEmpty()) {
            makePlayer(team.get(), Enums.Position.DEFENDER, 7.2, "John", "Terry");
            makePlayer(team.get(), Enums.Position.DEFENDER, 5.4, "Phil", "Jones");
            makePlayer(team.get(), Enums.Position.DEFENDER, 5.7, "Chris", "Smalling");
            makePlayer(team.get(), Enums.Position.MIDFIELDER, 8.5, "David", "Silva");

            makePlayer(team.get(), Enums.Position.MIDFIELDER, 8.2, "Bernado", "Silva");
            makePlayer(team.get(), Enums.Position.MIDFIELDER, 9.8, "Kevin", "DeBruyne");
            makePlayer(team.get(), Enums.Position.MIDFIELDER, 9.9, "Paul", "Pogba");
            makePlayer(team.get(), Enums.Position.ATTACKER, 8.8, "Paco", "");

            makePlayer(team.get(), Enums.Position.ATTACKER, 10.2, "Marcus", "Rashford");
            makePlayer(team.get(), Enums.Position.ATTACKER, 10.2, "Romelu", "Lukaku");
            makePlayer(team.get(), Enums.Position.GOALKEEPER, 12.5, "Dom", "Beesley");
            makePlayer(team.get(), Enums.Position.DEFENDER, 8.5, "Ed", "Main");

            makePlayer(team.get(), Enums.Position.DEFENDER, 7.5, "Joe", "Sutton");
            makePlayer(team.get(), Enums.Position.DEFENDER, 6.5, "Stevie", "");
            makePlayer(team.get(), Enums.Position.MIDFIELDER, 7.5, "Ollie", "Ferrao");
            makePlayer(team.get(), Enums.Position.MIDFIELDER, 6.5, "Eloka", "Philips");

            makePlayer(team.get(), Enums.Position.DEFENDER, 9.5, "Herbie", "");
            makePlayer(team.get(), Enums.Position.ATTACKER, 10.5, "Eduardo", "Garcia");
        }
    }


}

