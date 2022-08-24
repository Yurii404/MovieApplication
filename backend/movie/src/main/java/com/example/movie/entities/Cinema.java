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
@Table(name = "\"cinema\"")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cinema_generator")
    @SequenceGenerator(name = "cinema_generator", sequenceName = "CINEMA_SEQUENCE", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "cinema_name", length = 50, nullable = false)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_admin_id", nullable = false)
    private User user;

}