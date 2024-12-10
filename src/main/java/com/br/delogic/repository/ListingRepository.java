package com.br.delogic.repository;

import com.br.delogic.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {

    @Query("SELECT l.listingId FROM Listing l")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT l.listingId FROM Listing l")
    List<Long> findAllIds();

    @Query("SELECT l.listingId\n" +
            "FROM Listing l\n" +
            "JOIN Event e ON l.event.eventId = e.eventId\n" +
            "JOIN Venue v ON e.venue.venueId = v.venueId\n" +
            "WHERE e.date.calendarDate <= :contextDate\n" +
            "  AND (:categoryId IS NULL\n" +
            "       OR e.category.categoryId = :categoryId)\n" +
            "  AND (:city IS NULL\n" +
            "       OR v.city = :city)\n" +
            "  AND l.numberOfTickets > 0\n" +
            "ORDER BY e.date.calendarDate DESC LIMIT :limit")
    List<Long> findListingsByFilters(
            @Param("contextDate") LocalDate contextDate,
            @Param("categoryId") Long categoryId,
            @Param("city") String city,
            @Param("limit") Integer limit
    );

}
