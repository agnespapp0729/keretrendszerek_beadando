package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorManagerImpl implements DirectorManager {

    private final DirectorRepository directorRepository;

    private static Director convertDirectorEntityToModel(DirectorEntity directorEntity){
        return new Director(
                directorEntity.getDirectorId(),
                directorEntity.getDirectorQuality(),
                directorEntity.getAverageRevenue()
        );
    }

    private static DirectorEntity convertDirectorModelToEntity(Director director){
        return DirectorEntity.builder()
                .directorId(director.getDirectorId())
                .directorQuality(director.getDirectorQuality())
                .averageRevenue(director.getAverageR())
                .build();
    }

    @Override
    public Director record(Director director) throws DirectorNotFoundException, DirectorAlreadyExistsException {
        if(directorRepository.findById(director.getDirectorId()).isPresent()){
            throw new DirectorAlreadyExistsException();
        }

        DirectorEntity directorEntity = directorRepository.save(
                DirectorEntity.builder()
                        .directorId(director.getDirectorId())
                        .directorQuality(director.getDirectorQuality())
                        .averageRevenue(director.getAverageR())
                        .build()
        );
        return convertDirectorEntityToModel(directorEntity);
    }

    @Override
    public Director readById(int id) throws DirectorNotFoundException {
        Optional<DirectorEntity> entity = directorRepository.findById(id);
        if(entity.isEmpty()){
            throw new DirectorNotFoundException(String.format("Cannot find a director with this ID: %s", id));
        }
        return convertDirectorEntityToModel(entity.get());
    }

    @Override
    public Collection<Director> readAll() {
        return directorRepository.findAll().stream().map(DirectorManagerImpl::convertDirectorEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Director modify(Director director) throws DirectorNotFoundException {
        DirectorEntity entity = convertDirectorModelToEntity(director);
        return convertDirectorEntityToModel(directorRepository.save(entity));
    }

    @Override
    public void delete(Director director) throws DirectorNotFoundException {
        directorRepository.delete(convertDirectorModelToEntity(director));
    }
}
