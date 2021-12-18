package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
