package at.jugger.tracker.mapper;

import at.jugger.tracker.config.MapperConfig;
import at.jugger.tracker.domain.ApplicationPhaseEntity;
import at.jugger.tracker.domain.TournamentEntity;
import at.jugger.tracker.dto.Tournament;
import at.jugger.tracker.dto.TournamentData;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = ApplicationPhaseCreateMapper.class)
public interface TournamentCreateMapper {

    @Mappings({
            @Mapping(target = "id", source = "tournamentId")
    })
    Tournament toDto(TournamentEntity entity);

    List<Tournament> toDtos(List<TournamentEntity> entities);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "tournamentId", ignore = true)
    })
    TournamentEntity toEntity(TournamentData tournament, @MappingTarget TournamentEntity entity);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "tournamentId", ignore = true)
    })
    TournamentEntity toEntity(TournamentData tournament);

    @AfterMapping
    default void setTournamentParent(@MappingTarget TournamentEntity tournament) {
        for (ApplicationPhaseEntity applicationPhase : tournament.getApplicationPhases()) {
            applicationPhase.setTournament(tournament);
        }
    }
}
