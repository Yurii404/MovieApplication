package com.example.movie.repositories;

import com.example.movie.entities.AuditLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {
}
