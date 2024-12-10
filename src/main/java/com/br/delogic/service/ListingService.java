package com.br.delogic.service;

import com.br.delogic.model.Listing;
import com.br.delogic.model.json.PaginateObject;

public interface ListingService {

    PaginateObject<Listing> findAll(Integer pageNumber, Integer pageSize);

    PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize);

    Listing findById(Long id);
}
