package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.enums.Gender;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExitsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActorManagerImplTest {

    @Mock
    ActorRepository actorRepository;

    @InjectMocks
    ActorManagerImpl service;

    @Test
    void recordActorHappyPath() throws ActorAlreadyExitsException {

        //given
        Actor testActor = TestDataProvider.getMaleActor();
        ActorEntity testActorEntity = TestDataProvider.getMaleActorEntity();
        when(actorRepository.findById(any())).thenReturn(Optional.empty());
        when(actorRepository.save(any())).thenReturn(testActorEntity);
        //when
        Actor actual = service.record(testActor);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testActor);
    }

    @Test
    void readByIdHappyPath() throws ActorNotFoundException{

        //given
        when(actorRepository.findById(TestDataProvider.maleId))
                .thenReturn(Optional.of(TestDataProvider.getMaleActorEntity()));
        Actor expected = TestDataProvider.getMaleActor();
        //when
        Actor actual = service.readById(TestDataProvider.maleId);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void modifyActorHappyPath() throws ActorNotFoundException{

        //given
        Actor testActor = TestDataProvider.getMaleActor();
        ActorEntity testActorEntity = TestDataProvider.getMaleActorEntity();
        when(actorRepository.findById(testActor.getActorId()))
                .thenReturn(Optional.of(testActorEntity));
        when(actorRepository.save(any())).thenReturn(testActorEntity);
        //when
        Actor actual = service.modify(testActor);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testActor);
    }

    @Test
    void deleteActorHappyPath() throws ActorNotFoundException{

        //given
        Actor testActor = TestDataProvider.getMaleActor();
        ActorEntity testActorEntity = TestDataProvider.getMaleActorEntity();
        when(actorRepository.findById(testActor.getActorId()))
                .thenReturn(Optional.of(testActorEntity));
        //doNothing(actorRepository.delete(testActorEntity));
        //when
        service.delete(testActor);
    }

    @Test
    void readAllHappyPath(){
        //given
        List<ActorEntity> actorEntities = List.of(
                TestDataProvider.getMaleActorEntity(),
                TestDataProvider.getFemaleActorEntity()
        );
        Collection<Actor> expectedActors = List.of(
                TestDataProvider.getMaleActor(),
                TestDataProvider.getFemaleActor()
        );
        when(actorRepository.findAll()).thenReturn(actorEntities);
        //when
        Collection<Actor> actualActors = service.readAll();
        //then
        assertThat(actualActors)
                .usingRecursiveComparison()
                .isEqualTo(expectedActors);
    }

    @Test
    void recordActorAlreadyExistsException(){

        //given
        Actor testActor = TestDataProvider.getMaleActor();
        ActorEntity testActorEntity = TestDataProvider.getMaleActorEntity();
        when(actorRepository.findById(TestDataProvider.maleId))
                .thenReturn(Optional.ofNullable(testActorEntity));
        //when
        assertThatThrownBy(() -> service.record(testActor))
                .isInstanceOf(ActorAlreadyExitsException.class);
    }

    @Test
    void readByIdNotFoundException(){
        //given
        when(actorRepository.findById(TestDataProvider.unknown_ID))
                .thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.readById(TestDataProvider.unknown_ID))
                .isInstanceOf(ActorNotFoundException.class);
    }

    @Test
    void modifyActorNotFoundException(){
        //given
        Actor testActor = TestDataProvider.getMaleActor();
        when(actorRepository.findById(testActor.getActorId()))
                .thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.modify(testActor))
                .isInstanceOf(ActorNotFoundException.class);
    }

    @Test
    void deleteActorNotFoundException(){
        //given
        Actor testActor = TestDataProvider.getMaleActor();
        ActorEntity testActorEntity = TestDataProvider.getMaleActorEntity();
        //when
        assertThatThrownBy(() -> service.delete(testActor))
                .isInstanceOf(ActorNotFoundException.class);
    }



    private static class TestDataProvider{

        public static final int unknown_ID = 2;

        public static final int maleId = 566;

        public static final int femaleId = 1772500;

        public static Actor getMaleActor(){
            return new Actor(maleId, Gender.M, 4);
        }

        public static Actor getFemaleActor(){
            return new Actor(femaleId, Gender.F, 4);
        }

        public static ActorEntity getMaleActorEntity(){
            return ActorEntity.builder()
                    .actorId(maleId)
                    .actorGender(Gender.M)
                    .actorQuality(4)
                    .build();
        }

        public static ActorEntity getFemaleActorEntity() {
            return ActorEntity.builder()
                    .actorId(femaleId)
                    .actorGender(Gender.F)
                    .actorQuality(4)
                    .build();
        }
    }
}




