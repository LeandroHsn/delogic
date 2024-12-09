package com.br.delogic.service;

import com.br.delogic.model.User;
import com.br.delogic.model.json.PaginateObject;

public interface UserService {

    PaginateObject<User> findAll(Integer pageNumber, Integer pageSize);

    PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize);

    User findById(Long id);
}
