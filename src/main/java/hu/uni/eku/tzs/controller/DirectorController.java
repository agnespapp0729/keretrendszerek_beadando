package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.DirectorDto;
import hu.uni.eku.tzs.controller.dto.DirectorMapper;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.DirectorManager;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags="Directors")
@RequestMapping("/directors")
@RestController
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorManager directorManager;

    private final DirectorMapper directorMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<DirectorDto> readAllDirectors(){
        return directorManager.readAll()
                .stream()
                .map(directorMapper::directorTodirectorDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("ReadByID")
    @GetMapping(value="/{id}")
    public DirectorDto readById(@PathVariable int id) throws DirectorNotFoundException {
        try{
            return directorMapper.directorTodirectorDto(directorManager.readById(id));
        }
        catch(DirectorNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Record")
    @GetMapping(value = {""})
    public DirectorDto create (@Valid @RequestBody DirectorDto recordRequestDto) throws DirectorNotFoundException {
        Director director = directorMapper.directorDtoToDirector(recordRequestDto);
        try{
            Director recorded = directorManager.record(director);
            return directorMapper.directorTodirectorDto(recorded);
        }
        catch (DirectorAlreadyExistsException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @GetMapping(value = {""})
    public DirectorDto update(@Valid @RequestBody DirectorDto updateRequestDto){
        Director director = directorMapper.directorDtoToDirector(updateRequestDto);
        try{
            Director updateDirector = directorManager.modify(director);
            return directorMapper.directorTodirectorDto(updateDirector);
        }
        catch(DirectorNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @GetMapping(value = {""})
    public void delete(@RequestParam int id){
        try{
            directorManager.delete(directorManager.readById(id));
        }
        catch (DirectorNotFoundException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }




}
