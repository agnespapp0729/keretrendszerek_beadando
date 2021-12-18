package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.User;
import hu.uni.eku.tzs.service.exceptions.UserAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.UserNotFoundException;

import java.util.Collection;

public interface UserManager {
    User record(User user) throws UserAlreadyExistsException;

    User readById(int id) throws UserNotFoundException;

    Collection<User> readAll();

    User modify(User user) throws UserNotFoundException;

    void delete(User user) throws UserNotFoundException;
}
