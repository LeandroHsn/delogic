package com.br.delogic.service;

import com.br.delogic.model.Category;
import com.br.delogic.model.json.PaginateObject;

public interface CategoryService {

    PaginateObject<Category> findAll(Integer pageNumber, Integer pageSize);

    PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize);

    Category findById(Long id);
}
