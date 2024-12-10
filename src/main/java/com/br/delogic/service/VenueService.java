package com.br.delogic.service;

import com.br.delogic.model.Venue;
import com.br.delogic.model.json.PaginateObject;

public interface VenueService {

    PaginateObject<Venue> findAll(Integer pageNumber, Integer pageSize);

    PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize);

    Venue findById(Long id);
}
