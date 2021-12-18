package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.MovieDto;
import hu.uni.eku.tzs.controller.dto.MovieMapper;
import hu.uni.eku.tzs.enums.Gender;
import hu.uni.eku.tzs.enums.IsEnglish;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.MovieManager;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
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
public class MovieControllerTest {

    @Mock
    MovieManager movieManager;

    @Mock
    MovieMapper movieMapper;

    @InjectMocks
    MovieController controller;

    @Test
    void readAllHappyPath() {
        //given
        when(movieManager.readAll()).thenReturn(List.of(TestDataProvider.getMovie()));
        when(movieMapper.movieTomovieDto(any())).thenReturn(TestDataProvider.getMovieDto());
        Collection<MovieDto> expected = List.of(TestDataProvider.getMovieDto());
        //when
        Collection<MovieDto> actual = controller.readAllMovies();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdHappyPath() throws MovieNotFoundException {
        //given
        when(movieManager.readById(TestDataProvider.getMovie().getMovieId()))
                .thenReturn(TestDataProvider.getMovie());
        MovieDto expected = TestDataProvider.getMovieDto();
        when(movieMapper.movieTomovieDto(any())).thenReturn(TestDataProvider.getMovieDto());
        //when
        MovieDto actual = controller.readById(TestDataProvider.getMovie().getMovieId());
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createMovieHappyPath() throws MovieAlreadyExistsException {
        // given
        Movie movie566 = TestDataProvider.getMovie();
        MovieDto movie566Dto = TestDataProvider.getMovieDto();
        when(movieMapper.movieDtoTomovie(movie566Dto)).thenReturn(movie566);
        when(movieManager.record(movie566)).thenReturn(movie566);
        when(movieMapper.movieTomovieDto(movie566)).thenReturn(movie566Dto);
        // when
        MovieDto actual = controller.create(movie566Dto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(movie566Dto);
    }

    @Test
    void updateHappyPath() throws MovieNotFoundException {
        //given
        MovieDto requestDto = TestDataProvider.getMovieDto();
        Movie movie566 = TestDataProvider.getMovie();
        when(movieMapper.movieDtoTomovie(requestDto)).thenReturn(movie566);
        when(movieManager.modify(movie566)).thenReturn(movie566);
        when(movieMapper.movieTomovieDto(movie566)).thenReturn(requestDto);
        MovieDto expected = TestDataProvider.getMovieDto();
        //when
        MovieDto response = controller.update(requestDto);
        //then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws MovieNotFoundException {
        //given
        Movie movie566 = TestDataProvider.getMovie();
        when(movieManager.readById(TestDataProvider.ID)).thenReturn(movie566);
        doNothing().when(movieManager).delete(movie566);
        //when
        controller.delete(TestDataProvider.ID);
    }

    @Test
    void readByIdMovieNotFoundException() throws MovieNotFoundException {
        //given
        when(movieManager.readById(TestDataProvider.getMovie().getMovieId()))
                .thenThrow(new MovieNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.readById(TestDataProvider.getMovie().getMovieId());
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void createMovieThrowsAlreadyExistException() throws MovieAlreadyExistsException {
        // given
        Movie movie566 = TestDataProvider.getMovie();
        MovieDto movie566Dto = TestDataProvider.getMovieDto();
        when(movieMapper.movieDtoTomovie(movie566Dto)).thenReturn(movie566);
        when(movieManager.record(movie566)).thenThrow(new MovieAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(movie566Dto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateThrowsNotFoundException() throws MovieNotFoundException {
        //given
        MovieDto requestDto = TestDataProvider.getMovieDto();
        Movie movie566 = TestDataProvider.getMovie();
        when(movieMapper.movieDtoTomovie(requestDto)).thenReturn(movie566);
        when(movieManager.modify(movie566)).thenThrow(new MovieNotFoundException());
        //when then
        assertThatThrownBy(() -> {
            controller.update(requestDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamWhenMovieNotFound() throws MovieNotFoundException {
        //given
        final int notFoundMovieId = TestDataProvider.ID;
        doThrow(new MovieNotFoundException()).when(movieManager).readById(notFoundMovieId);
        //when then
        assertThatThrownBy(() -> controller.delete(notFoundMovieId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int ID = 1673647;

        public static Movie getMovie() {
            return new Movie(ID, 3, IsEnglish.F, "France", 3);
        }

        public static MovieDto getMovieDto() {
            return MovieDto.builder()
                    .movieId(ID)
                    .year(3)
                    .isEnglish(IsEnglish.F)
                    .country("France")
                    .runningTime(3)
                    .build();
        }

    }

}