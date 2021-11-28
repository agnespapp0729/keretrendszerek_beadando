package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExitsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorManagerImpl implements ActorManager{

    private final ActorRepository actorRepository;

    private static Actor convertActorEntityToModel(ActorEntity actorEntity){
        return new Actor(
                actorEntity.getActorId(),
                actorEntity.getActorGender(),
                actorEntity.getActorQuality()
        );
    }

    private static ActorEntity convertActorModelToEntity(Actor actor){
        return ActorEntity.builder()
                .actorId(actor.getActorId())
                .actorGender(actor.getActorGender())
                .actorQuality(actor.getActorQuality())
                .build();
    }

    @Override
    public Actor record(Actor actor) throws ActorAlreadyExitsException {
        if(actorRepository.findById(actor.getActorId()).isPresent()){
            throw new ActorAlreadyExitsException();
        }

        ActorEntity actorEntity = actorRepository.save(
                ActorEntity.builder()
                        .actorId(actor.getActorId())
                        .actorGender(actor.getActorGender())
                        .actorQuality(actor.getActorQuality())
                        .build()
        );
        return convertActorEntityToModel(actorEntity);
    }

    @Override
    public Actor readById(int id) throws ActorNotFoundException {
        Optional<ActorEntity> entity = actorRepository.findById(id);
        if(entity.isEmpty()){
            throw new ActorNotFoundException(String.format("Cannot find actor with this ID: %s", id));
        }
        return convertActorEntityToModel(entity.get());
    }

    @Override
    public Collection<Actor> readAll() {
        return actorRepository.findAll().stream().map(ActorManagerImpl::convertActorEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Actor modify(Actor actor) throws ActorNotFoundException {
        ActorEntity entity = convertActorModelToEntity(actor);
        return convertActorEntityToModel(actorRepository.save(entity));
    }

    @Override
    public void delete(Actor actor) throws ActorNotFoundException {
        actorRepository.delete(convertActorModelToEntity(actor));
    }
}
