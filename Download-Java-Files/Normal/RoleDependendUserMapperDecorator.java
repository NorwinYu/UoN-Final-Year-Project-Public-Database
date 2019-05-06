package at.jugger.tracker.mapper.decorator;

import at.jugger.tracker.domain.UserEntity;
import at.jugger.tracker.dto.User;
import at.jugger.tracker.mapper.UserMapper;
import at.jugger.tracker.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.stream.Collectors;

import static at.jugger.tracker.dto.UserData.RoleEnum.ADMIN;

public abstract class RoleDependendUserMapperDecorator implements UserMapper {

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Autowired
    private SecurityService securityService;

    @Override
    public User toDto(UserEntity entity) {
        User dto = delegate.toDto(entity);

        if (dto != null &&
                !securityService.currentUserHasRole(ADMIN.name()) &&
                !securityService.isCurrentUser(dto)) {
            dto.email(null)
                    .role(null);
        }

        return dto;
    }

    @Override
    public List<User> toDtos(List<UserEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}