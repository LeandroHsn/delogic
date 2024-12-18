package com.br.delogic.repository;

import com.br.delogic.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


    @Query("SELECT u.userId FROM User u")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT u.userId FROM User u")
    List<Long> findAllIds();

}
