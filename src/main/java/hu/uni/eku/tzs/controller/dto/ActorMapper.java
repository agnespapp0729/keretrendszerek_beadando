package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorDto actorToactorDto(Actor actor);

    Actor actorDtoToActor(ActorDto actorDto);


}
