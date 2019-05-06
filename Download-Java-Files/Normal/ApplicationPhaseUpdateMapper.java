package at.jugger.tracker.mapper;

import at.jugger.tracker.config.MapperConfig;
import at.jugger.tracker.domain.ApplicationPhaseEntity;
import at.jugger.tracker.dto.ApplicationPhase;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface ApplicationPhaseUpdateMapper {

    @Mappings({
            @Mapping(target = "id", source = "applicationPhaseId")
    })
    ApplicationPhase toDto(ApplicationPhaseEntity entity);

    List<ApplicationPhase> toDtos(List<ApplicationPhaseEntity> entities);

    @InheritInverseConfiguration
    ApplicationPhaseEntity toEntity(ApplicationPhase applicationPhase, @MappingTarget ApplicationPhaseEntity entity);

    @InheritInverseConfiguration
    ApplicationPhaseEntity toEntity(ApplicationPhase applicationPhase);
}
