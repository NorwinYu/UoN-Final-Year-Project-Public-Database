package at.jugger.tracker.mapper;

import at.jugger.tracker.config.MapperConfig;
import at.jugger.tracker.domain.TrainingEntity;
import at.jugger.tracker.dto.Training;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface TrainingMapper {

    @Mappings({
            @Mapping(target = "participantIds", source = "participants")
    })
    Training toDto(TrainingEntity entity);

    List<Training> toDtos(List<TrainingEntity> entities);

    @InheritInverseConfiguration
    @Mappings(
            @Mapping(target = "trainingId", ignore = true)
    )
    TrainingEntity toEntity(Training training, @MappingTarget TrainingEntity entity);

    @InheritConfiguration
    @Mappings(
            @Mapping(target = "trainingId", ignore = true)
    )
    TrainingEntity toEntity(Training training);
}
