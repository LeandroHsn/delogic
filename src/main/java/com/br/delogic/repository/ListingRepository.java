package com.br.delogic.repository;

import com.br.delogic.model.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {

    @Query("SELECT l.listingId FROM Listing l")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT l.listingId FROM Listing l")
    List<Long> findAllIds();

}
