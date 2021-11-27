package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto movieTomovieDto(Movie movie);

    Actor movieDtoTomovie(MovieDto movieDto);
}
