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
@Table(name = "\"movie_room\"")
public class MovieRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_room_generator")
    @SequenceGenerator(name = "movie_room_generator", sequenceName = "MOVIE_ROOM_SEQUENCE", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    @Column(name = "num_of_seats", nullable = false)
    private int numOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

}
