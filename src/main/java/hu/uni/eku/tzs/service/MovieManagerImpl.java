package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.MovieRepository;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieManagerImpl implements MovieManager {

    private final MovieRepository movieRepository;

    private static Movie convertMovieEntityToModel(MovieEntity movieEntity) {
        return new Movie(
            movieEntity.getMovieId(),
            movieEntity.getYear(),
            movieEntity.getIsEnglish(),
            movieEntity.getCountry(),
            movieEntity.getRunningTime()
        );
    }

    private static MovieEntity convertMovieModelToEntity(Movie movie) {
        return MovieEntity.builder()
            .movieId(movie.getMovieId())
            .year(movie.getYear())
            .isEnglish(movie.getIsEnglish())
            .country(movie.getCountry())
            .runningTime(movie.getRunningTime())
            .build();
    }

    @Override
    public Movie record(Movie movie) throws MovieAlreadyExistsException {
        if (movieRepository.findById(movie.getMovieId()).isPresent()) {
            throw new MovieAlreadyExistsException();
        }

        MovieEntity movieEntity = movieRepository.save(
            MovieEntity.builder()
                .movieId(movie.getMovieId())
                .year(movie.getYear())
                .isEnglish(movie.getIsEnglish())
                .country(movie.getCountry())
                .runningTime(movie.getRunningTime())
                .build()
        );
        return convertMovieEntityToModel(movieEntity);
    }

    @Override
    public Movie readById(int id) throws MovieNotFoundException {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (entity.isEmpty()) {
            throw new MovieNotFoundException(String.format("Cannot find a movie with this ID: %s", id));
        }
        return convertMovieEntityToModel(entity.get());
    }

    @Override
    public Collection<Movie> readAll() {
        return movieRepository.findAll().stream().map(MovieManagerImpl::convertMovieEntityToModel)
            .collect(Collectors.toList());
    }

    @Override
    public Movie modify(Movie movie) throws MovieNotFoundException {
        MovieEntity entity = convertMovieModelToEntity(movie);
        if (movieRepository.findById(movie.getMovieId()).isEmpty()) {
            throw new MovieNotFoundException("Cannot find a movie with this id");
        }
        return convertMovieEntityToModel(movieRepository.save(entity));
    }

    @Override
    public void delete(Movie movie) throws MovieNotFoundException {
        if (movieRepository.findById(movie.getMovieId()).isEmpty()) {
            throw new MovieNotFoundException("Can not find a movie with this id");
        }
        movieRepository.delete(convertMovieModelToEntity(movie));
    }
}
