package com.example.movie.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "\"audit_log_method_invocation\"")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_generator")
    @SequenceGenerator(name = "audit_generator", sequenceName = "AUDIT_SEQUENCE", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "method_name", nullable = false)
    private String methodName;

    @Column(name = "arguments")
    private String arguments;

    @Column(name = "return_value")
    private String returnValue;

    @Column(name = "thrown_exception")
    private String thrownException;

    @Column(name = "invocation_time", nullable = false)
    private LocalDateTime invocationTime;
}
