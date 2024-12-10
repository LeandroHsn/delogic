package com.br.delogic.repository;

import com.br.delogic.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("SELECT e.eventId FROM Event e")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT e.eventId FROM Event e")
    List<Long> findAllIds();
}
