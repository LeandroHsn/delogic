package com.br.delogic.repository;

import com.br.delogic.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

    @Query("SELECT s.saleId FROM Sale s")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT s.saleId FROM Sale s")
    List<Long> findAllIds();
}
