package com.example.movie.repositories;

import com.example.movie.entities.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select m from User m where m.id = ?1")
    Optional<User> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<User> findUserByUsername(String username);
}
