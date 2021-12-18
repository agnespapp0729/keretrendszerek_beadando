package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorRepository;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectorManagerImplTest {

    @Mock
    DirectorRepository directorRepository;

    @InjectMocks
    DirectorManagerImpl service;

    @Test
    void recordDirectorHappyPath() throws DirectorAlreadyExistsException {
        //given
        Director testDirector = TestDataProvider.get754Director();
        DirectorEntity testDirectorEntity = TestDataProvider.get754DirectorEntity();
        when(directorRepository.findById(any())).thenReturn(Optional.empty());
        when(directorRepository.save(any())).thenReturn(testDirectorEntity);
        //when
        Director actual = service.record(testDirector);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDirector);
    }

    @Test
    void readByIdHappyPath() throws DirectorNotFoundException {
        //given
        when(directorRepository.findById(TestDataProvider.ID1))
                .thenReturn(Optional.of(TestDataProvider.get754DirectorEntity()));
        Director expected = TestDataProvider.get754Director();
        //when
        Director actual = service.readById(TestDataProvider.ID1);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void modifyDirectorHappyPath() throws DirectorNotFoundException {
        //given
        Director testDirector = TestDataProvider.get754Director();
        DirectorEntity testDirectorEntity = TestDataProvider.get754DirectorEntity();
        when(directorRepository.findById(testDirector.getDirectorId()))
                .thenReturn(Optional.of(testDirectorEntity));
        when(directorRepository.save(any())).thenReturn(testDirectorEntity);
        //when
        Director actual = service.modify(testDirector);
        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(testDirector);
    }

    @Test
    void deleteDirectorHappyPath() throws DirectorNotFoundException {
        //given
        Director testDirector = TestDataProvider.get754Director();
        DirectorEntity testDirectorEntity = TestDataProvider.get754DirectorEntity();
        when(directorRepository.findById(testDirector.getDirectorId()))
                .thenReturn(Optional.of(testDirectorEntity));
        //doNothing(directorRepository.delete(testDirectorEntity));
        //when
        service.delete(testDirector);
    }

    @Test
    void readAllHappyPath() {
        //given
        List<DirectorEntity> directorEntities = List.of(
                TestDataProvider.get754DirectorEntity(),
                TestDataProvider.get54305DirectorEntity()
        );
        Collection<Director> expectedDirectors = List.of(
                TestDataProvider.get754Director(),
                TestDataProvider.get54305Director()
        );
        when(directorRepository.findAll()).thenReturn(directorEntities);
        //when
        Collection<Director> actualDirectors = service.readAll();
        //then
        assertThat(actualDirectors)
                .usingRecursiveComparison()
                .isEqualTo(expectedDirectors);
    }

    @Test
    void recordDirectorAlreadyExistsException() {
        //given
        Director testDirector = TestDataProvider.get754Director();
        DirectorEntity testDirectorEntity = TestDataProvider.get754DirectorEntity();
        when(directorRepository.findById(TestDataProvider.ID1))
                .thenReturn(Optional.ofNullable(testDirectorEntity));
        //when
        assertThatThrownBy(() -> service.record(testDirector))
                .isInstanceOf(DirectorAlreadyExistsException.class);
    }

    @Test
    void readByIdNotFoundException() {
        //given
        when(directorRepository.findById(TestDataProvider.UNKNOWN_ID))
                .thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readById(TestDataProvider.UNKNOWN_ID))
                .isInstanceOf(DirectorNotFoundException.class);
    }

    @Test
    void modifyDirectorThrowNotFoundException() {
        //given
        Director testDirector = TestDataProvider.get754Director();
        when(directorRepository.findById(testDirector.getDirectorId()))
                .thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.modify(testDirector))
                .isInstanceOf(DirectorNotFoundException.class);
    }

    @Test
    void deleteDirectorNotFoundException() {
        //given
        Director testDirector = TestDataProvider.get754Director();
        DirectorEntity testDirectorEntity = TestDataProvider.get754DirectorEntity();
        //when then
        assertThatThrownBy(() -> service.delete(testDirector))
                .isInstanceOf(DirectorNotFoundException.class);
    }



    private static class TestDataProvider {

        public static final int UNKNOWN_ID = 2;

        public static final int ID1 = 754;

        public static final int ID2 = 54305;

        public static Director get754Director() {
            return new Director(ID1, 4, 4);
        }

        public static Director get54305Director() {
            return new Director(ID2, 4, 2);
        }

        public static DirectorEntity get754DirectorEntity() {
            return DirectorEntity.builder()
                    .directorId(ID1)
                    .directorQuality(4)
                    .averageRevenue(4)
                    .build();
        }

        public static DirectorEntity get54305DirectorEntity() {
            return DirectorEntity.builder()
                    .directorId(ID2)
                    .directorQuality(4)
                    .averageRevenue(2)
                    .build();
        }
    }
}