package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.MovieRepository;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.enums.Gender;
import hu.uni.eku.tzs.enums.IsEnglish;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
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
public class MovieManagerImplTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieManagerImpl service;

    @Test
    void recordMovieHappyPath() throws MovieAlreadyExistsException {

        //given
        Movie testMovie = TestDataProvider.getFranceMovie();
        MovieEntity testMovieEntity = TestDataProvider.getFranceMovieEntity();
        when(movieRepository.findById(any())).thenReturn(Optional.empty());
        when(movieRepository.save(any())).thenReturn(testMovieEntity);
        //when
        Movie actual = service.record(testMovie);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testMovie);
    }

    @Test
    void readByIdHappyPath() throws MovieNotFoundException {

        //given
        when(movieRepository.findById(TestDataProvider.FranceMovieId))
                .thenReturn(Optional.of(TestDataProvider.getFranceMovieEntity()));
        Movie expected = MovieManagerImplTest.TestDataProvider.getFranceMovie();
        //when
        Movie actual = service.readById(TestDataProvider.FranceMovieId);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void modifyMovieHappyPath() throws MovieNotFoundException{

        //given
        Movie testMovie = TestDataProvider.getFranceMovie();
        MovieEntity testMovieEntity = TestDataProvider.getFranceMovieEntity();
        when(movieRepository.findById(testMovie.getMovieId()))
                .thenReturn(Optional.of(testMovieEntity));
        when(movieRepository.save(any())).thenReturn(testMovieEntity);
        //when
        Movie actual = service.modify(testMovie);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testMovie);
    }

    @Test
    void deleteMovieHappyPath() throws MovieNotFoundException{

        //given
        Movie testMovie = TestDataProvider.getFranceMovie();
        MovieEntity testMovieEntity = TestDataProvider.getFranceMovieEntity();
        when(movieRepository.findById(testMovie.getMovieId()))
                .thenReturn(Optional.of(testMovieEntity));
        //doNothing(movieRepository.delete(testMovieEntity));
        //when
        service.delete(testMovie);
    }

    @Test
    void readAllHappyPath(){
        //given
        List<MovieEntity> movieEntities = List.of(
                TestDataProvider.getFranceMovieEntity(),
                TestDataProvider.getUSAMovieEntity()
        );
        Collection<Movie> expectedMovies = List.of(
                TestDataProvider.getFranceMovie(),
                TestDataProvider.getUSAMovie()
        );
        when(movieRepository.findAll()).thenReturn(movieEntities);
        //when
        Collection<Movie> actualMovies = service.readAll();
        //then
        assertThat(actualMovies)
                .usingRecursiveComparison()
                .isEqualTo(expectedMovies);
    }

    @Test
    void recordMovieAlreadyExistsException(){

        //given
        Movie testMovie = MovieManagerImplTest.TestDataProvider.getFranceMovie();
        MovieEntity testMovieEntity = MovieManagerImplTest.TestDataProvider.getFranceMovieEntity();
        when(movieRepository.findById(TestDataProvider.FranceMovieId))
                .thenReturn(Optional.ofNullable(testMovieEntity));
        //when
        assertThatThrownBy(() -> service.record(testMovie))
                .isInstanceOf(MovieAlreadyExistsException.class);
    }

    @Test
    void readByIdNotFoundException(){
        //given
        when(movieRepository.findById(TestDataProvider.unknown_id))
                .thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.readById(TestDataProvider.unknown_id))
                .isInstanceOf(MovieNotFoundException.class);
    }

    @Test
    void modifyMovieNotFoundException(){
        //given
        Movie testMovie = MovieManagerImplTest.TestDataProvider.getFranceMovie();
        when(movieRepository.findById(testMovie.getMovieId()))
                .thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.modify(testMovie))
                .isInstanceOf(MovieNotFoundException.class);
    }

    @Test
    void deleteMovieNotFoundException(){
        //given
        Movie testMovie = TestDataProvider.getFranceMovie();
        MovieEntity testMovieEntity = TestDataProvider.getFranceMovieEntity();
        //when
        assertThatThrownBy(() -> service.delete(testMovie))
                .isInstanceOf(MovieNotFoundException.class);
    }

    public static class TestDataProvider{
        
        public static final int unknown_id = 2;
        
        public static final int FranceMovieId = 1673647;
        
        public static final int USAMovieId = 1729005;

        public static Movie getFranceMovie(){
            return new Movie(FranceMovieId, 3 , IsEnglish.F, "France", 3);
        }

        public static Movie getUSAMovie(){
            return new Movie(USAMovieId, 4, IsEnglish.T, "USA", 2);
        }

        public static MovieEntity getFranceMovieEntity(){
            return MovieEntity.builder()
                    .movieId(FranceMovieId)
                    .year(3)
                    .isEnglish(IsEnglish.F)
                    .country("France")
                    .runningTime(3)
                    .build();
        }

        public static MovieEntity getUSAMovieEntity() {
            return MovieEntity.builder()
                    .movieId(USAMovieId)
                    .year(4)
                    .isEnglish(IsEnglish.T)
                    .country("USA")
                    .runningTime(2)
                    .build();
        }
    }
    
}
