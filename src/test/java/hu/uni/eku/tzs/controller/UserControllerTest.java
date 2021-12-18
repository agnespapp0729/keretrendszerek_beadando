package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.UserDto;
import hu.uni.eku.tzs.controller.dto.UserMapper;
import hu.uni.eku.tzs.model.User;
import hu.uni.eku.tzs.service.UserManager;
import hu.uni.eku.tzs.service.exceptions.UserAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserManager userManager;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserController controller;

    @Test
    void readAllHappyPath() {
        //given
        when(userManager.readAll()).thenReturn(List.of(TestDataProvider.getUser()));
        when(userMapper.userTouserDto(any())).thenReturn(TestDataProvider.getUserDto());
        Collection<UserDto> expected = List.of(TestDataProvider.getUserDto());
        //when
        Collection<UserDto> actual = controller.readAllUsers();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdHappyPath() throws UserNotFoundException {
        //given
        when(userManager.readById(TestDataProvider.getUser().getUserId()))
                .thenReturn(TestDataProvider.getUser());
        UserDto expected = TestDataProvider.getUserDto();
        when(userMapper.userTouserDto(any())).thenReturn(TestDataProvider.getUserDto());
        //when
        UserDto actual = controller.readById(TestDataProvider.getUser().getUserId());
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createUserHappyPath() throws UserAlreadyExistsException {
        // given
        User user566 = TestDataProvider.getUser();
        UserDto user566Dto = TestDataProvider.getUserDto();
        when(userMapper.userDtoTouser(user566Dto)).thenReturn(user566);
        when(userManager.record(user566)).thenReturn(user566);
        when(userMapper.userTouserDto(user566)).thenReturn(user566Dto);
        // when
        UserDto actual = controller.create(user566Dto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(user566Dto);
    }

    @Test
    void updateHappyPath() throws UserNotFoundException {
        //given
        UserDto requestDto = TestDataProvider.getUserDto();
        User user566 = TestDataProvider.getUser();
        when(userMapper.userDtoTouser(requestDto)).thenReturn(user566);
        when(userManager.modify(user566)).thenReturn(user566);
        when(userMapper.userTouserDto(user566)).thenReturn(requestDto);
        UserDto expected = TestDataProvider.getUserDto();
        //when
        UserDto response = controller.update(requestDto);
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws UserNotFoundException {
        //given
        User user566 = TestDataProvider.getUser();
        when(userManager.readById(TestDataProvider.ID)).thenReturn(user566);
        doNothing().when(userManager).delete(user566);
        //when
        controller.delete(TestDataProvider.ID);
    }

    @Test
    void readByIdUserNotFoundException() throws UserNotFoundException {
        //given
        when(userManager.readById(TestDataProvider.getUser().getUserId()))
                .thenThrow(new UserNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.readById(TestDataProvider.getUser().getUserId());
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void createUserThrowsAlreadyExistException() throws UserAlreadyExistsException {
        // given
        User user566 = TestDataProvider.getUser();
        UserDto user566Dto = TestDataProvider.getUserDto();
        when(userMapper.userDtoTouser(user566Dto)).thenReturn(user566);
        when(userManager.record(user566)).thenThrow(new UserAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(user566Dto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateThrowsNotFoundException() throws UserNotFoundException {
        //given
        UserDto requestDto = TestDataProvider.getUserDto();
        User user566 = TestDataProvider.getUser();
        when(userMapper.userDtoTouser(requestDto)).thenReturn(user566);
        when(userManager.modify(user566)).thenThrow(new UserNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.update(requestDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamWhenUserNotFound() throws UserNotFoundException {
        //given
        final int notFoundUserId = TestDataProvider.ID;
        doThrow(new UserNotFoundException()).when(userManager).readById(notFoundUserId);
        //when then
        assertThatThrownBy(() -> controller.delete(notFoundUserId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 99;

        public static User getUser() {
            return new User(ID, "1", "F", "2");
        }

        public static UserDto getUserDto() {
            return UserDto.builder()
                    .userId(ID)
                    .userAge("1")
                    .userGender("F")
                    .occupation("2")
                    .build();
        }

    }

}