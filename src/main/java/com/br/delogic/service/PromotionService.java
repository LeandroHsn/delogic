package com.br.delogic.service;

import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.model.json.PromotionFilter;

public interface PromotionService {

    PaginateObject<Long> findPromotion(PromotionFilter filter);

    PaginateObject<Long> findPromotion(PromotionFilter filter, boolean withoutPredicate);

}
