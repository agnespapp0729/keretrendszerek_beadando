package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;

import java.util.Collection;

public interface DirectorManager {
    Director record(Director director) throws DirectorAlreadyExistsException;

    Director readById(int id) throws DirectorNotFoundException;

    Collection<Director> readAll();

    Director modify(Director director) throws DirectorNotFoundException;
    void delete(Director director) throws DirectorNotFoundException;
}
