package com.example.movie.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "USER_SEQUENCE", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "user_username", length = 50, nullable = false)
    private String username;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email", length = 100, nullable = false)
    private String email;

    @Column(name = "user_firstname", length = 50, nullable = false)
    private String firstName;

    @Column(name = "user_second_name", length = 50, nullable = false)
    private String secondName;

    @Column(name = "user_phone", length = 50, nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}