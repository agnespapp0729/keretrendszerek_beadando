package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ActorDto;
import hu.uni.eku.tzs.controller.dto.ActorMapper;
import hu.uni.eku.tzs.enums.Gender;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.ActorManager;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExitsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
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
public class ActorControllerTest {

    @Mock
    ActorManager actorManager;

    @Mock
    ActorMapper actorMapper;

    @InjectMocks
    ActorController controller;

    @Test
    void readAllHappyPath() {
        //given
        when(actorManager.readAll()).thenReturn(List.of(TestDataProvider.getActor()));
        when(actorMapper.actorToactorDto(any())).thenReturn(TestDataProvider.getActorDto());
        Collection<ActorDto> expected = List.of(TestDataProvider.getActorDto());
        //when
        Collection<ActorDto> actual = controller.readAllActors();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdHappyPath() throws ActorNotFoundException {
        //given
        when(actorManager.readById(TestDataProvider.getActor().getActorId()))
                .thenReturn(TestDataProvider.getActor());
        ActorDto expected = TestDataProvider.getActorDto();
        when(actorMapper.actorToactorDto(any())).thenReturn(TestDataProvider.getActorDto());
        //when
        ActorDto actual = controller.readById(TestDataProvider.getActor().getActorId());
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createActorHappyPath() throws ActorAlreadyExitsException {
        // given
        Actor actor566 = TestDataProvider.getActor();
        ActorDto actor566Dto = TestDataProvider.getActorDto();
        when(actorMapper.actorDtoToActor(actor566Dto)).thenReturn(actor566);
        when(actorManager.record(actor566)).thenReturn(actor566);
        when(actorMapper.actorToactorDto(actor566)).thenReturn(actor566Dto);
        // when
        ActorDto actual = controller.create(actor566Dto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(actor566Dto);
    }

    @Test
    void updateHappyPath() throws ActorNotFoundException {
        //given
        ActorDto requestDto = TestDataProvider.getActorDto();
        Actor actor566 = TestDataProvider.getActor();
        when(actorMapper.actorDtoToActor(requestDto)).thenReturn(actor566);
        when(actorManager.modify(actor566)).thenReturn(actor566);
        when(actorMapper.actorToactorDto(actor566)).thenReturn(requestDto);
        ActorDto expected = TestDataProvider.getActorDto();
        //when
        ActorDto response = controller.update(requestDto);
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws ActorNotFoundException {
        //given
        Actor actor566 = TestDataProvider.getActor();
        when(actorManager.readById(TestDataProvider.ID)).thenReturn(actor566);
        doNothing().when(actorManager).delete(actor566);
        //when
        controller.delete(TestDataProvider.ID);
    }

    @Test
    void readByIdActorNotFoundException() throws ActorNotFoundException {
        //given
        when(actorManager.readById(TestDataProvider.getActor().getActorId()))
                .thenThrow(new ActorNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.readById(TestDataProvider.getActor().getActorId());
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void createActorThrowsAlreadyExistException() throws ActorAlreadyExitsException {
        // given
        Actor actor566 = TestDataProvider.getActor();
        ActorDto actor566Dto = TestDataProvider.getActorDto();
        when(actorMapper.actorDtoToActor(actor566Dto)).thenReturn(actor566);
        when(actorManager.record(actor566)).thenThrow(new ActorAlreadyExitsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(actor566Dto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateThrowsNotFoundException() throws ActorNotFoundException {
        //given
        ActorDto requestDto = TestDataProvider.getActorDto();
        Actor actor566 = TestDataProvider.getActor();
        when(actorMapper.actorDtoToActor(requestDto)).thenReturn(actor566);
        when(actorManager.modify(actor566)).thenThrow(new ActorNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.update(requestDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamWhenActorNotFound() throws ActorNotFoundException {
        //given
        final int notFoundActorId = TestDataProvider.ID;
        doThrow(new ActorNotFoundException()).when(actorManager).readById(notFoundActorId);
        //when then
        assertThatThrownBy(() -> controller.delete(notFoundActorId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 566;

        public static Actor getActor() {
            return new Actor(ID, Gender.M, 4);
        }

        public static ActorDto getActorDto() {
            return ActorDto.builder()
                    .actorId(ID)
                    .actorGender(Gender.M)
                    .actorQuality(4)
                    .build();
        }

    }

}