package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;

import java.util.Collection;

public interface MovieManager {
    Movie record(Movie movie) throws MovieAlreadyExistsException;

    Movie readById(int id) throws MovieNotFoundException;

    Collection<Movie> readAll();

    Movie modify(Movie movie) throws MovieNotFoundException;
    void delete(Movie movie) throws MovieNotFoundException;

}
