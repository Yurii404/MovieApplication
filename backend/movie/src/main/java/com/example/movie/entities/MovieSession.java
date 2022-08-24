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
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "\"movie_session\"")
public class MovieSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_session_generator")
    @SequenceGenerator(name = "movie_session_generator", sequenceName = "MOVIE_SESSION_SEQUENCE", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "movie_name", length = 100, nullable = false)
    private String nameOfMovie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_room_id", nullable = false)
    private MovieRoom movieRoom;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "remaining_tickets", nullable = false)
    private int numOfTickets;

}
