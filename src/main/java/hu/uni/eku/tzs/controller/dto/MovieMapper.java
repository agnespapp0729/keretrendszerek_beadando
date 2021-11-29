package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto movieTomovieDto(Movie movie);

    Movie movieDtoTomovie(MovieDto movieDto);
}
