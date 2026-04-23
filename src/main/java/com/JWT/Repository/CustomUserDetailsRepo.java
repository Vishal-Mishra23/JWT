package com.JWT.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JWT.entityManager.UserDetailsEntity;

@Repository
public interface CustomUserDetailsRepo extends JpaRepository<UserDetailsEntity, Long> {

	Optional<UserDetailsEntity> findByUserName(String username);

}
