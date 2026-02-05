package com.empleos.electrohome.repository;

import com.empleos.electrohome.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

   Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
