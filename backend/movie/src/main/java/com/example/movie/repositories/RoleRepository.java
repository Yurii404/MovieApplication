package com.example.movie.repositories;

import com.example.movie.entities.Role;
import com.example.movie.entities.Ticket;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(String roleName);
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select r from Role r where r.id = ?1")
    Optional<Role> findById(Long id);

}
