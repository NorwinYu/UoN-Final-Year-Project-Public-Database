package at.jugger.tracker.mapper;

import at.jugger.tracker.config.MapperConfig;
import at.jugger.tracker.domain.LoginTokenEntity;
import at.jugger.tracker.service.dto.LoginToken;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = InsecureUserMapper.class)
public interface LoginTokenMapper {
    LoginToken toDto(LoginTokenEntity entity);
}
