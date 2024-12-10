package com.br.delogic.service;

import com.br.delogic.model.Date;
import com.br.delogic.model.User;
import com.br.delogic.model.json.PaginateObject;

public interface DateService {

    PaginateObject<Date> findAll(Integer pageNumber, Integer pageSize);

    PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize);

    Date findById(Long id);
}
