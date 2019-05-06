package at.jugger.tracker.mapper;

import at.jugger.tracker.config.MapperConfig;
import at.jugger.tracker.domain.UserEntity;
import at.jugger.tracker.dto.User;
import at.jugger.tracker.dto.UserData;
import at.jugger.tracker.mapper.decorator.RoleDependendUserMapperDecorator;
import at.jugger.tracker.repository.UserRepository;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = {UserRepository.class})
@DecoratedWith(RoleDependendUserMapperDecorator.class)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "userId")
    })
    User toDto(UserEntity entity);

    List<User> toDtos(List<UserEntity> entities);

    @Mappings({
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "trackable", defaultValue = "false")
    })
    UserEntity toEntity(UserData user, @MappingTarget UserEntity entity);

    @Mappings({
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "trackable", defaultValue = "false")
    })
    UserEntity toEntity(UserData newUser);

    @InheritInverseConfiguration
    UserEntity toEntity(User user);

    UserEntity toEntity(Long id);

    default Long toId(UserEntity user) {
        return user.getUserId();
    }

    List<Long> toIds(List<UserEntity> users);

}
