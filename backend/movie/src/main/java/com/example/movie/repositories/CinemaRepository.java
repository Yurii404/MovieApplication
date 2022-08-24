package com.example.movie.repositories;

import com.example.movie.entities.Cinema;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select c from Cinema c where c.id = ?1")
    Optional<Cinema> findById(Long id);
}
