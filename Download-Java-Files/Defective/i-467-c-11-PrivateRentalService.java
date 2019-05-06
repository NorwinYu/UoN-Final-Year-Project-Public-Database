package pl.put.boardgamemanager.private_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.put.boardgamemanager.ListDTO;
import pl.put.boardgamemanager.Utils;
import pl.put.boardgamemanager.game.GameRepository;
import pl.put.boardgamemanager.game_copy.GameCopyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivateRentalService {

    @Autowired
    private PrivateRentalRepository privateRentalRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameCopyRepository gameCopyRepository;

    public PrivateRentalDTO get(Long id) {
        PrivateRental rental = privateRentalRepository.findById(id).orElse(null);
        if(rental == null) {
            PrivateRentalDTO dto = new PrivateRentalDTO();
            dto.setErrorMessage("There is no private rental with the given id");
            return dto;
        }
        else return Utils.assignGameNameTo(rental.toDTO(), gameRepository, gameCopyRepository);
    }

    public ListDTO<PrivateRentalDTO> all() {
        ListDTO<PrivateRentalDTO> resultDTO = new ListDTO<>();
        resultDTO.setValues(privateRentalRepository.findAll().stream()
                .map(PrivateRental::toDTO)
                .map(dto -> Utils.assignGameNameTo(dto, gameRepository, gameCopyRepository))
                .collect(Collectors.toList()));
        return resultDTO;
    }

    public PrivateRentalDTO create(PrivateRentalDTO dto) {
        PrivateRental rental = new PrivateRental();
        rental.updateParamsFrom(dto);

        try {
            privateRentalRepository.save(rental);
            return Utils.assignGameNameTo(rental.toDTO(), gameRepository, gameCopyRepository);
        }
        catch(DataIntegrityViolationException ex) {
            dto.setErrorMessage("Given data violates data constraints");
            return dto;
        }
    }

    public PrivateRentalDTO update(PrivateRentalDTO dto) {
        return privateRentalRepository.findById(dto.getId())
                .map(existingRental -> {
                    existingRental.updateParamsFrom(dto);

                    try {
                        privateRentalRepository.save(existingRental);
                        return Utils.assignGameNameTo(existingRental.toDTO(), gameRepository, gameCopyRepository);
                    }
                    catch(DataIntegrityViolationException ex) {
                        dto.setErrorMessage("Given data violates data constraints");
                        return dto;
                    }

                })
                .orElseGet(() -> create(dto));
    }

    public void delete(Long id) {
        privateRentalRepository.deleteById(id);
    }

}
