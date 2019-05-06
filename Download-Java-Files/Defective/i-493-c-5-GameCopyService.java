package pl.put.boardgamemanager.game_copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.put.boardgamemanager.ListDTO;
import pl.put.boardgamemanager.ValueDTO;
import pl.put.boardgamemanager.game.Game;
import pl.put.boardgamemanager.game.GameRepository;
import pl.put.boardgamemanager.game.GameWithCopiesSetDTO;
import pl.put.boardgamemanager.private_rental.PrivateRental;
import pl.put.boardgamemanager.private_rental.PrivateRentalRepository;
import pl.put.boardgamemanager.tournament.Tournament;
import pl.put.boardgamemanager.tournament.TournamentRepository;
import pl.put.boardgamemanager.tournament_rental.TournamentRental;
import pl.put.boardgamemanager.tournament_rental.TournamentRentalRepository;
import pl.put.boardgamemanager.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameCopyService {

    @Autowired
    private GameCopyRepository gameCopyRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PrivateRentalRepository privateRentalRepository;

    @Autowired
    private TournamentRentalRepository tournamentRentalRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    private List<GameCopy> getTournamentRentalGameCopies(LocalDateTime rentalTime, Integer duration) {
        PrivateRental desiredRental = new PrivateRental();
        desiredRental.setStartTime(rentalTime);
        desiredRental.setDuration(duration);

        return tournamentRepository
                .findAll()
                .stream()
                .filter(tournament -> Utils.isEventDuringAnother(tournament, desiredRental))
                .map(Tournament::getId)
                .map(tournamentId -> tournamentRentalRepository.findAllByTournamentId(tournamentId))
                .flatMap(List::stream)
                .map(TournamentRental::getCopyId)
                .map(copyId -> gameCopyRepository.findById(copyId).orElse(null))
                .collect(Collectors.toList());
    }

    private List<GameCopy> getBusyRentalCopies(LocalDateTime rentalTime, Integer duration) {
        PrivateRental desiredRental = new PrivateRental();
        desiredRental.setStartTime(rentalTime);
        desiredRental.setDuration(duration);

        return privateRentalRepository
                .findAll()
                .stream()
                .filter(rental -> Utils.isEventDuringAnother(rental, desiredRental))
                .map(PrivateRental::getCopyId)
                .map(copyId -> gameCopyRepository.findById(copyId).orElse(null))
                .collect(Collectors.toList());
    }

    public GameCopyDTO get(Long id) {
        GameCopy gameCopy = gameCopyRepository.findById(id).orElse(null);
        if (gameCopy == null) {
            GameCopyDTO dto = new GameCopyDTO();
            dto.setErrorMessage("There is no game copy with the given id");
            return dto;
        } else return gameCopy.toDTO();
    }

    public ValueDTO<Integer> countGames(Long gameId) {
        ValueDTO<Integer> resultDTO = new ValueDTO<>();
        Game game = gameRepository.findById(gameId).orElse(null);
        if(game == null) resultDTO.setErrorMessage("There is no game with the given id");
        else resultDTO.setValue(gameCopyRepository.findAllByGameId(gameId).size());
        return resultDTO;
    }

    public ListDTO<GameCopyDTO> all() {
        ListDTO<GameCopyDTO> resultDTO = new ListDTO<>();
        resultDTO.setValues(gameCopyRepository.findAll().stream()
                .map(GameCopy::toDTO)
                .collect(Collectors.toList()));
        return resultDTO;
    }

    public GameCopyDTO create(GameCopyDTO dto) {
        GameCopy gameCopy = new GameCopy();
        gameCopy.updateParamsFrom(dto);

        try {
            gameCopyRepository.save(gameCopy);
            return gameCopy.toDTO();
        }
        catch(DataIntegrityViolationException ex) {
            dto.setErrorMessage("Given data violates data constraints");
            return dto;
        }

    }

    private List<GameCopy> getAvailableGameCopiesFor(LocalDateTime startTime, Integer duration) {
        List<GameCopy> allCopies = gameCopyRepository.findAll();
        allCopies.removeAll(getTournamentRentalGameCopies(startTime, duration));
        allCopies.removeAll(getBusyRentalCopies(startTime, duration));

        return allCopies;
    }

    public ListDTO<GameWithCopiesSetDTO> getAvailableGameWithCopiesSetDTOs(LocalDateTime startTime, Integer duration) {
        List<Game> allGames = gameRepository.findAll();

        List<GameCopy> availableGameCopies = getAvailableGameCopiesFor(startTime, duration);

        ListDTO<GameWithCopiesSetDTO> resultDTO = new ListDTO<>();
        resultDTO.setValues(allGames
                .stream()
                .map(game -> {
                    List<GameCopy> copies = new ArrayList<>();
                    availableGameCopies.forEach(copy -> {
                        if (copy.getGameId().equals(game.getId())) copies.add(copy);
                    });

                    GameWithCopiesSetDTO dto = new GameWithCopiesSetDTO();
                    dto.setGame(game);
                    dto.setGameCopies(copies);

                    return dto;
                })
                .collect(Collectors.toList()));
        return resultDTO;

    }

    public ListDTO<GameCopyNameDTO> getAvailableGameCopyNameDTOsFor(LocalDateTime startTime, Integer duration) {
        ListDTO<GameCopyNameDTO> resultDTO = new ListDTO<>();
        resultDTO.setValues(getAvailableGameCopiesFor(startTime, duration)
                .stream()
                .map(gameCopy -> {
                    Game game = gameRepository.findById(gameCopy.getGameId()).orElse(null);
                    if (game == null) return null;
                    else {
                        GameCopyNameDTO dto = new GameCopyNameDTO();
                        dto.setName(game.getName());
                        dto.setCopyId(gameCopy.getId());
                        return dto;
                    }
                })
                .filter(Utils.distinctByKey(GameCopyNameDTO::getName))
                .collect(Collectors.toList()));

        return resultDTO;
    }

    public GameCopyDTO update(GameCopyDTO dto) {
        return gameCopyRepository.findById(dto.getId())
                .map(existingCopy -> {
                    existingCopy.updateParamsFrom(dto);

                    try {
                        gameCopyRepository.save(existingCopy);
                        return existingCopy.toDTO();
                    }
                    catch(DataIntegrityViolationException ex) {
                        dto.setErrorMessage("Given data violates data constraints");
                        return dto;
                    }
                })
                .orElseGet(() -> create(dto));
    }

    public void delete(Long id) {
        gameCopyRepository.deleteById(id);
    }
}
