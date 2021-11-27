package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Director;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorDto actorToactorDto(Director director);

    Actor actorDtoToActor(DirectorDto directorDto);

}
