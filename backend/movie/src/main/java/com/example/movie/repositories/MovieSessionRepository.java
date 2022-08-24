package com.example.movie.repositories;

import com.example.movie.entities.MovieSession;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface MovieSessionRepository extends CrudRepository<MovieSession, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select m from MovieSession m where m.id = ?1")
    Optional<MovieSession> findById(Long id);
}
