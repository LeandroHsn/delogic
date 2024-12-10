package com.br.delogic.service.impl;
import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Sale;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.SaleRepository;
import com.br.delogic.service.SaleService;
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
public class SaleServiceImpl implements SaleService {

        @Autowired
        private SaleRepository saleRepository;

        public PaginateObject<Sale> findAll(Integer pageNumber, Integer pageSize) {
                return findAllElementPageable(pageNumber, pageSize);
        }

        @Transactional(readOnly = true)
        public PaginateObject<Sale> findAllElementPageable(Integer pageNumber, Integer pageSize) {

                Pageable pageable;
                List<Sale> listSales;
                int size;

                if (Objects.nonNull(pageNumber) && pageNumber > 0 && Objects.nonNull(pageSize)) {
                        pageable = PageRequest.of(pageNumber - 1, pageSize);
                        Page<Sale> list = saleRepository.findAll(pageable);
                        listSales = new ArrayList<>(list.getContent());
                        size = (int) list.getTotalElements();
                } else {
                        listSales = saleRepository.findAll();
                        size = listSales.size();
                }

                return new PaginateObject<>(size, listSales);
        }

        public PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize) {

                int page = (pageNumber != null) ? pageNumber  : 0;
                int size = (pageSize != null) ? pageSize : Integer.MAX_VALUE;
                int totalElements;
                Pageable pageable = size == Integer.MAX_VALUE ? Pageable.unpaged() : PageRequest.of(page - 1, size);
                List<Long> ids;

                if (size == Integer.MAX_VALUE) {
                        ids = saleRepository.findAllIds();
                        totalElements = ids.size();
                } else {
                        Page<Long> idPage = saleRepository.findAllIds(pageable);
                        ids = idPage.getContent();
                        totalElements = (int) idPage.getTotalElements();
                }

                return new PaginateObject<>(totalElements, ids);
        }

        public Sale findById(Long id) {
                return findSaleById(id);
        }

        public Sale findSaleById(Long id) {
                return saleRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.NOT_FOUND.getMessage("Venda")));
        }
}




