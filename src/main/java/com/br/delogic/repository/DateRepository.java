package com.br.delogic.repository;

import com.br.delogic.model.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<Date, Long>, JpaSpecificationExecutor<Date> {

    @Query("SELECT d.id FROM Date d")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT d.id FROM Date d")
    List<Long> findAllIds();

}
