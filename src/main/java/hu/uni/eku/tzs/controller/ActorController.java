package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ActorDto;
import hu.uni.eku.tzs.controller.dto.ActorMapper;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.ActorManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags="Actors")
@RequestMapping("/actors")
@RestController
@RequiredArgsConstructor
public class ActorController {
    private final ActorManager actorManager;

    private final ActorMapper actorMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<ActorDto> readAllActors(){
        return ActorManager.readAll()
                .stream()
                .map(actorMapper::actorToactorDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @GetMapping(value = {""})
    public ActorDto create (@Valid @RequestBody ActorDto recordRequestDto){
        Actor actor = actorMapper.actorDtoToActor(recordRequestDto);
        try{
            Actor recorded = actorManager.record();
            return actorMapper.actorToactorDto(recorded)
        }
        catch (ActorAlreadyExistException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage);
        }
    }

}
