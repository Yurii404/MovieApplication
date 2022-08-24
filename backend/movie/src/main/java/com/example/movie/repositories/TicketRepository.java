package com.example.movie.repositories;

import com.example.movie.entities.MovieSession;
import com.example.movie.entities.Ticket;
import com.example.movie.entities.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query(value = """ 
            SELECT `USER_ID`
            FROM "ticket"
            INNER JOIN "movie_session" ON "movie_session".id = "ticket".session_id
            WHERE `MOVIE_ROOM_ID` = ?1
            GROUP by `USER_ID`
            ORDER BY COUNT(`USER_ID`) DESC LIMIT 1;
            """
            , nativeQuery = true)
    long findTheMostActiveUserByRoomId(long roomId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select m from Ticket m where m.id = ?1")
    Optional<Ticket> findById(Long id);
}
