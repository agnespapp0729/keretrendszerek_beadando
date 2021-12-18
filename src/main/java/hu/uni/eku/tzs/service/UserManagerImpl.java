package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.UserRepository;
import hu.uni.eku.tzs.dao.entity.UserEntity;
import hu.uni.eku.tzs.model.User;
import hu.uni.eku.tzs.service.exceptions.UserNotFoundException;
import hu.uni.eku.tzs.service.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {

    private final UserRepository userRepository;

    private static User convertUserEntityToModel(UserEntity userEntity) {
        return new User(
            userEntity.getUserId(),
            userEntity.getAge(),
            userEntity.getUserGender(),
            userEntity.getOccupation()
        );
    }

    private static UserEntity convertUserModelToEntity(User user) {
        return UserEntity.builder()
            .userId(user.getUserId())
            .age(user.getAge())
            .userGender(user.getUserGender())
            .occupation(user.getOccupation())
            .build();
    }

    @Override
    public User record(User user) throws UserAlreadyExistsException {
        if (userRepository.findById(user.getUserId()).isPresent()) {

            throw new UserAlreadyExistsException();
        }

        UserEntity userEntity = userRepository.save(
            UserEntity.builder()
                .userId(user.getUserId())
                .age(user.getAge())
                .userGender(user.getUserGender())
                .occupation(user.getOccupation())
                .build()
        );
        return convertUserEntityToModel(userEntity);
    }

    @Override
    public User readById(int id) throws UserNotFoundException {
        Optional<UserEntity> entity = userRepository.findById(id);
        if (entity.isEmpty()) {
            throw new UserNotFoundException(String.format("Cannot find a user with this ID: %s", id));
        }
        return convertUserEntityToModel(entity.get());
    }

    @Override
    public Collection<User> readAll() {
        return userRepository.findAll().stream().map(UserManagerImpl::convertUserEntityToModel)
            .collect(Collectors.toList());
    }

    @Override
    public User modify(User user) throws UserNotFoundException {
        UserEntity entity = convertUserModelToEntity(user);
        if (userRepository.findById(user.getUserId()).isEmpty()) {
            throw new UserNotFoundException("Can not modify a user with this id");
        }
        return convertUserEntityToModel(userRepository.save(entity));
    }

    @Override
    public void delete(User user) throws UserNotFoundException {
        if (userRepository.findById(user.getUserId()).isEmpty()) {
            throw new UserNotFoundException("Can not delete a user with this id");
        }
        userRepository.delete(convertUserModelToEntity(user));
    }
}
