package com.br.delogic.service;

import com.br.delogic.model.Sale;
import com.br.delogic.model.json.PaginateObject;

public interface SaleService {

    PaginateObject<Sale> findAll(Integer pageNumber, Integer pageSize);

    PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize);

    Sale findById(Long id);
}
