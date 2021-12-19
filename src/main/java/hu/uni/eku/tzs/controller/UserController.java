package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.UserDto;
import hu.uni.eku.tzs.controller.dto.UserMapper;
import hu.uni.eku.tzs.model.User;
import hu.uni.eku.tzs.service.UserManager;
import hu.uni.eku.tzs.service.exceptions.UserAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@Api(tags = "Users")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserManager userManager;

    private final UserMapper userMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {""})
    public Collection<UserDto> readAllUsers() {
        return userManager.readAll()
            .stream()
            .map(userMapper::userTouserDto)
            .collect(Collectors.toList());
    }

    @ApiOperation("ReadByID")
    @GetMapping(value = "/{id}")
    public UserDto readById(@PathVariable int id) throws UserNotFoundException {
        try {
            return userMapper.userTouserDto(userManager.readById(id));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Record")
    @PostMapping(value = {""})
    public UserDto create(@Valid @RequestBody UserDto recordRequestDto) throws UserAlreadyExistsException {
        User user = userMapper.userDtoTouser(recordRequestDto);
        try {
            User recorded = userManager.record(user);
            return userMapper.userTouserDto(recorded);
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {""})
    public UserDto update(@Valid @RequestBody UserDto updateRequestDto) {
        User user = userMapper.userDtoTouser(updateRequestDto);
        try {
            User updateUser = userManager.modify(user);
            return userMapper.userTouserDto(updateUser);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {""})
    public void delete(@RequestParam int id) {
        try {
            userManager.delete(userManager.readById(id));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
