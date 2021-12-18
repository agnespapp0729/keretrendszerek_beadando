package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExitsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;

import java.util.Collection;

public interface ActorManager {

    Actor record(Actor actor) throws ActorAlreadyExitsException;

    Actor readById(int id) throws ActorNotFoundException;

    Collection<Actor> readAll();

    Actor modify(Actor actor) throws ActorNotFoundException;

    void delete(Actor actor) throws ActorNotFoundException;

}
