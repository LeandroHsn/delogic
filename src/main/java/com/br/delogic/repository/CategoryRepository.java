package com.br.delogic.repository;

import com.br.delogic.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query("SELECT c.categoryId FROM Category c")
    Page<Long> findAllCategoryIds(Pageable pageable);

    @Query("SELECT c.categoryId FROM Category c")
    List<Long> findAllCategoryIds();

}
