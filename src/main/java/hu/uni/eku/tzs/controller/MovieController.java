package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.MovieDto;
import hu.uni.eku.tzs.controller.dto.MovieMapper;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.MovieManager;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "Movies")
@RequestMapping("/movies")
@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieManager movieManager;

    private final MovieMapper movieMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<MovieDto> readAllMovies() {
        return movieManager.readAll()
            .stream()
            .map(movieMapper::movieTomovieDto)
            .collect(Collectors.toList());
    }

    @ApiOperation("ReadByID")
    @GetMapping(value = "/{id}")
    public MovieDto readById(@PathVariable int id) throws MovieNotFoundException {
        try {
            return movieMapper.movieTomovieDto(movieManager.readById(id));
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Record")
    @GetMapping(value = {""})
    public MovieDto create(@Valid @RequestBody MovieDto recordRequestDto) throws MovieAlreadyExistsException {
        Movie movie = movieMapper.movieDtoTomovie(recordRequestDto);
        try {
            Movie recorded = movieManager.record(movie);
            return movieMapper.movieTomovieDto(recorded);
        } catch (MovieAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @GetMapping(value = {""})
    public MovieDto update(@Valid @RequestBody MovieDto updateRequestDto) {
        Movie movie = movieMapper.movieDtoTomovie(updateRequestDto);
        try {
            Movie updateMovie = movieManager.modify(movie);
            return movieMapper.movieTomovieDto(updateMovie);
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @GetMapping(value = {""})
    public void delete(@RequestParam int id) {
        try {
            movieManager.delete(movieManager.readById(id));
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
