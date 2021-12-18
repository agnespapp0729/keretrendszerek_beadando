package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.UserRepository;
import hu.uni.eku.tzs.dao.entity.UserEntity;
import hu.uni.eku.tzs.dao.entity.UserEntity;
import hu.uni.eku.tzs.enums.Gender;
import hu.uni.eku.tzs.model.User;
import hu.uni.eku.tzs.model.User;
import hu.uni.eku.tzs.service.exceptions.UserAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserManagerImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserManagerImpl service;

    @Test
    void recordUserHappyPath() throws UserAlreadyExistsException {

        //given
        User testUser = TestDataProvider.getUser();
        UserEntity testUserEntity = TestDataProvider.getUserEntity();
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(testUserEntity);
        //when
        User actual = service.record(testUser);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testUser);
    }

    @Test
    void readByIdHappyPath() throws UserNotFoundException {

        //given
        when(userRepository.findById(TestDataProvider.id1))
                .thenReturn(Optional.of(TestDataProvider.getUserEntity()));
        User expected = TestDataProvider.getUser();
        //when
        User actual = service.readById(TestDataProvider.id1);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void modifyUserHappyPath() throws UserNotFoundException{

        //given
        User testUser = TestDataProvider.getUser();
        UserEntity testUserEntity = TestDataProvider.getUserEntity();
        when(userRepository.findById(testUser.getUserId()))
                .thenReturn(Optional.of(testUserEntity));
        when(userRepository.save(any())).thenReturn(testUserEntity);
        //when
        User actual = service.modify(testUser);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testUser);
    }

    @Test
    void deleteUserHappyPath() throws UserNotFoundException{

        //given
        User testUser = TestDataProvider.getUser();
        UserEntity testUserEntity = TestDataProvider.getUserEntity();
        when(userRepository.findById(testUser.getUserId()))
                .thenReturn(Optional.of(testUserEntity));
        //doNothing(userRepository.delete(testUserEntity));
        //when
        service.delete(testUser);
    }

    @Test
    void readAllHappyPath(){
        //given
        List<UserEntity> userEntities = List.of(
                TestDataProvider.getUserEntity(),
                TestDataProvider.getAnotherEntity()
        );
        Collection<User> expectedUsers = List.of(
                TestDataProvider.getUser(),
                TestDataProvider.getAnother()
        );
        when(userRepository.findAll()).thenReturn(userEntities);
        //when
        Collection<User> actualUsers = service.readAll();
        //then
        assertThat(actualUsers)
                .usingRecursiveComparison()
                .isEqualTo(expectedUsers);
    }

    @Test
    void recordUserAlreadyExistsException(){

        //given
        User testUser = TestDataProvider.getUser();
        UserEntity testUserEntity = TestDataProvider.getUserEntity();
        when(userRepository.findById(TestDataProvider.id1))
                .thenReturn(Optional.ofNullable(testUserEntity));
        //when
        assertThatThrownBy(() -> service.record(testUser))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void readByIdNotFoundException(){
        //given
        when(userRepository.findById(TestDataProvider.unknown_ID))
                .thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.readById(TestDataProvider.unknown_ID))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void modifyUserNotFoundException(){
        //given
        User testUser = TestDataProvider.getUser();
        when(userRepository.findById(testUser.getUserId()))
                .thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.modify(testUser))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUserNotFoundException(){
        //given
        User testUser = TestDataProvider.getUser();
        UserEntity testUserEntity = TestDataProvider.getUserEntity();
        //when
        assertThatThrownBy(() -> service.delete(testUser))
                .isInstanceOf(UserNotFoundException.class);
    }


    private static class TestDataProvider{

        public static final int unknown_ID = 2;

        public static final int id1 = 99;

        public static final int id2 = 3034;

        public static User getUser(){
            return new User(id1, "1", "F", "2");
        }

        public static User getAnother(){
            return new User(id2, "18", "F", "5");
        }

        public static UserEntity getUserEntity(){
            return UserEntity.builder()
                    .userId(id1)
                    .age("1")
                    .userGender("F")
                    .occupation("2")
                    .build();
        }

        public static UserEntity getAnotherEntity() {
            return UserEntity.builder()
                    .userId(id2)
                    .age("18")
                    .userGender("F")
                    .occupation("5")
                    .build();
        }
    }

}
