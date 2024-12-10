package com.br.delogic.service.impl;
import com.br.delogic.model.Listing;
import com.br.delogic.model.User;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.model.json.PromotionFilter;
import com.br.delogic.repository.ListingRepository;
import com.br.delogic.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PromotionServiceImpl implements PromotionService {

        @Autowired
        private ListingRepository listingRepository;

        private static final int LIMIT = 10;

        public PaginateObject<Long> findPromotion(PromotionFilter promotionFilter) {
                List<Long> ids = new ArrayList<>();
                List<Listing> list = listingRepository.findAll(promotionFilter);
                list.stream().limit(LIMIT).forEach(listing -> {
                        ids.add(listing.getListingId());
                });
                return new PaginateObject<>(ids.size(), ids);
        }

        public PaginateObject<Long> findPromotion(PromotionFilter promotionFilter, boolean withoutPredicate) {
                List<Long> ids = listingRepository.findListingsByFilters(promotionFilter.getContextDate(),
                                                                         promotionFilter.getCategoryId(),
                                                                         promotionFilter.getEventCity(),
                                                                         LIMIT);
                return new PaginateObject<>(ids.size(), ids);
        }
}




