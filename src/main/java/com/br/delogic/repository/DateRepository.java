package com.br.delogic.repository;

import com.br.delogic.model.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<Date, Integer>, JpaSpecificationExecutor<Date> {

}
