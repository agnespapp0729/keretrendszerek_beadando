package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ActorDto;
import hu.uni.eku.tzs.controller.dto.ActorMapper;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.ActorManager;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExitsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
        return actorManager.readAll()
                .stream()
                .map(actorMapper::actorToactorDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("ReadByID")
    @GetMapping(value="/{id}")
    public ActorDto readById(@PathVariable int id) throws ActorNotFoundException {
        try{
            return actorMapper.actorToactorDto(actorManager.readById(id));
        }
        catch(ActorNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Record")
    @GetMapping(value = {""})
    public ActorDto create (@Valid @RequestBody ActorDto recordRequestDto){
        Actor actor = actorMapper.actorDtoToActor(recordRequestDto);
        try{
            Actor recorded = actorManager.record(actor);
            return actorMapper.actorToactorDto(recorded);
        }
        catch (ActorAlreadyExitsException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @GetMapping(value = {""})
    public ActorDto update(@Valid @RequestBody ActorDto updateRequestDto){
        Actor actor = actorMapper.actorDtoToActor(updateRequestDto);
        try{
            Actor updateActor = actorManager.modify(actor);
            return actorMapper.actorToactorDto(updateActor);
        }
        catch(ActorNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @GetMapping(value = {""})
    public void delete(@RequestParam int id){
        try{
            actorManager.delete(actorManager.readById(id));
        }
        catch (ActorNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }






}
