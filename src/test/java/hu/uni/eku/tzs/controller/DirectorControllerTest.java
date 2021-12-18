package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.DirectorDto;
import hu.uni.eku.tzs.controller.dto.DirectorMapper;
import hu.uni.eku.tzs.enums.Gender;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.DirectorManager;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
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
public class DirectorControllerTest {

    @Mock
    DirectorManager directorManager;

    @Mock
    DirectorMapper directorMapper;

    @InjectMocks
    DirectorController controller;

    @Test
    void readAllHappyPath() {
        //given
        when(directorManager.readAll()).thenReturn(List.of(TestDataProvider.getDirector()));
        when(directorMapper.directorTodirectorDto(any())).thenReturn(TestDataProvider.getDirectorDto());
        Collection<DirectorDto> expected = List.of(TestDataProvider.getDirectorDto());
        //when
        Collection<DirectorDto> actual = controller.readAllDirectors();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdHappyPath() throws DirectorNotFoundException {
        //given
        when(directorManager.readById(TestDataProvider.getDirector().getDirectorId()))
                .thenReturn(TestDataProvider.getDirector());
        DirectorDto expected = TestDataProvider.getDirectorDto();
        when(directorMapper.directorTodirectorDto(any())).thenReturn(TestDataProvider.getDirectorDto());
        //when
        DirectorDto actual = controller.readById(TestDataProvider.getDirector().getDirectorId());
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createDirectorHappyPath() throws DirectorAlreadyExistsException {
        // given
        Director director566 = TestDataProvider.getDirector();
        DirectorDto director566Dto = TestDataProvider.getDirectorDto();
        when(directorMapper.directorDtoToDirector(director566Dto)).thenReturn(director566);
        when(directorManager.record(director566)).thenReturn(director566);
        when(directorMapper.directorTodirectorDto(director566)).thenReturn(director566Dto);
        // when
        DirectorDto actual = controller.create(director566Dto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(director566Dto);
    }

    @Test
    void updateHappyPath() throws DirectorNotFoundException {
        //given
        DirectorDto requestDto = TestDataProvider.getDirectorDto();
        Director director566 = TestDataProvider.getDirector();
        when(directorMapper.directorDtoToDirector(requestDto)).thenReturn(director566);
        when(directorManager.modify(director566)).thenReturn(director566);
        when(directorMapper.directorTodirectorDto(director566)).thenReturn(requestDto);
        DirectorDto expected = TestDataProvider.getDirectorDto();
        //when
        DirectorDto response = controller.update(requestDto);
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws DirectorNotFoundException {
        //given
        Director director566 = TestDataProvider.getDirector();
        when(directorManager.readById(TestDataProvider.ID)).thenReturn(director566);
        doNothing().when(directorManager).delete(director566);
        //when
        controller.delete(TestDataProvider.ID);
    }

    @Test
    void readByIdDirectorNotFoundException() throws DirectorNotFoundException {
        //given
        when(directorManager.readById(TestDataProvider.getDirector().getDirectorId()))
                .thenThrow(new DirectorNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.readById(TestDataProvider.getDirector().getDirectorId());
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void createDirectorThrowsAlreadyExistException() throws DirectorAlreadyExistsException {
        // given
        Director director566 = TestDataProvider.getDirector();
        DirectorDto director566Dto = TestDataProvider.getDirectorDto();
        when(directorMapper.directorDtoToDirector(director566Dto)).thenReturn(director566);
        when(directorManager.record(director566)).thenThrow(new DirectorAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(director566Dto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateThrowsNotFoundException() throws DirectorNotFoundException {
        //given
        DirectorDto requestDto = TestDataProvider.getDirectorDto();
        Director director566 = TestDataProvider.getDirector();
        when(directorMapper.directorDtoToDirector(requestDto)).thenReturn(director566);
        when(directorManager.modify(director566)).thenThrow(new DirectorNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.update(requestDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamWhenDirectorNotFound() throws DirectorNotFoundException {
        //given
        final int notFoundDirectorId = TestDataProvider.ID;
        doThrow(new DirectorNotFoundException()).when(directorManager).readById(notFoundDirectorId);
        //when then
        assertThatThrownBy(() -> controller.delete(notFoundDirectorId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 746;

        public static Director getDirector() {
            return new Director(ID, 4, 4);
        }

        public static DirectorDto getDirectorDto() {
            return DirectorDto.builder()
                    .directorId(ID)
                    .directorQuality(4)
                    .revenue(4)
                    .build();
        }

    }

}