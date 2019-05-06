package at.jugger.tracker.service.impl;

import at.jugger.tracker.domain.UserEntity;
import at.jugger.tracker.dto.User;
import at.jugger.tracker.dto.UserData;
import at.jugger.tracker.exceptions.UserNotFoundException;
import at.jugger.tracker.mapper.UserMapper;
import at.jugger.tracker.repository.TrainingRepository;
import at.jugger.tracker.repository.UserRepository;
import at.jugger.tracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TrainingRepository trainingRepository;

    UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public User getUser(@NotNull Long id) {
        return userMapper.toDto(userRepository.findByUserId(id));
    }

    @Override
    public User getUserByEmail(@NotNull String email) {
        User user = userMapper.toDto(userRepository.findByEmail(email));
        if (user != null)
            user.setEmail(email);
        return user;
    }

    @Override
    public @NotNull List<User> getUsers() {
        return userMapper.toDtos(userRepository.findAll());
    }

    @Override
    public @NotNull User createUser(@NotNull UserData newUser) {
        UserEntity userEntity = userMapper.toEntity(newUser);

        userEntity = userRepository.save(userEntity);

        return userMapper.toDto(userEntity);
    }

    @Override
    @Transactional
    public @NotNull User updateUser(Long id, UserData user) {
        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) {
            throw new UserNotFoundException("id", Long.toString(id));
        }

        if (userEntity.isTrackable() && !user.getTrackable()) {
            trainingRepository.deleteAllParticipationsForUser(userEntity.getUserId());
        }

        userEntity = userMapper.toEntity(user, userEntity);
        userEntity = userRepository.save(userEntity);

        return userMapper.toDto(userEntity);
    }

    @Override
    public void deleteUser(@NotNull Long id) {
        userRepository.deleteById(id);
    }
}
