package com.br.delogic.repository;

import com.br.delogic.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long>, JpaSpecificationExecutor<Venue> {

    @Query("SELECT v.venueId FROM Venue v")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT v.venueId FROM Venue v")
    List<Long> findAllIds();

}
