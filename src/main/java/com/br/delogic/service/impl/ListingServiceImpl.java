package com.br.delogic.service.impl;
import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Listing;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.ListingRepository;
import com.br.delogic.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ListingServiceImpl implements ListingService {

        @Autowired
        private ListingRepository listingRepository;

        public PaginateObject<Listing> findAll(Integer pageNumber, Integer pageSize) {
                return findAllElementPageable(pageNumber, pageSize);
        }

        @Transactional(readOnly = true)
        public PaginateObject<Listing> findAllElementPageable(Integer pageNumber, Integer pageSize) {

                Pageable pageable;
                List<Listing> listListings;
                int size;

                if (Objects.nonNull(pageNumber) && pageNumber > 0 && Objects.nonNull(pageSize)) {
                        pageable = PageRequest.of(pageNumber - 1, pageSize);
                        Page<Listing> list = listingRepository.findAll(pageable);
                        listListings = new ArrayList<>(list.getContent());
                        size = (int) list.getTotalElements();
                } else {
                        listListings = listingRepository.findAll();
                        size = listListings.size();
                }

                return new PaginateObject<>(size, listListings);
        }

        public PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize) {

                int page = (pageNumber != null) ? pageNumber  : 0;
                int size = (pageSize != null) ? pageSize : Integer.MAX_VALUE;
                int totalElements;
                Pageable pageable = size == Integer.MAX_VALUE ? Pageable.unpaged() : PageRequest.of(page - 1, size);
                List<Long> ids;

                if (size == Integer.MAX_VALUE) {
                        ids = listingRepository.findAllIds();
                        totalElements = ids.size();
                } else {
                        Page<Long> idPage = listingRepository.findAllIds(pageable);
                        ids = idPage.getContent();
                        totalElements = (int) idPage.getTotalElements();
                }

                return new PaginateObject<>(totalElements, ids);
        }

        public Listing findById(Long id) {
                return findListingById(id);
        }

        public Listing findListingById(Long id) {
                return listingRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.NOT_FOUND.getMessage("Listagem")));
        }
}




