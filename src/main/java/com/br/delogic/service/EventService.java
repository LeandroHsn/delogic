package com.br.delogic.service;

import com.br.delogic.model.Event;
import com.br.delogic.model.json.PaginateObject;

public interface EventService {

    PaginateObject<Event> findAll(Integer pageNumber, Integer pageSize);

    PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize);

    Event findById(Long id);
}
