package com.br.delogic.service.impl;
import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Category;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.CategoryRepository;
import com.br.delogic.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

        @Autowired
        private CategoryRepository categoryRepository;

        public PaginateObject<Category> findAll(Integer pageNumber, Integer pageSize) {
                return findAllElementPageable(pageNumber, pageSize);
        }

        @Transactional(readOnly = true)
        public PaginateObject<Category> findAllElementPageable(Integer pageNumber, Integer pageSize) {

                Pageable pageable;
                List<Category> listCategorys;
                int size;

                if (Objects.nonNull(pageNumber) && pageNumber > 0 && Objects.nonNull(pageSize)) {
                        pageable = PageRequest.of(pageNumber - 1, pageSize);
                        Page<Category> list = categoryRepository.findAll(pageable);
                        listCategorys = new ArrayList<>(list.getContent());
                        size = (int) list.getTotalElements();
                } else {
                        listCategorys = categoryRepository.findAll();
                        size = listCategorys.size();
                }

                return new PaginateObject<>(size, listCategorys);
        }

        public PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize) {

                int page = (pageNumber != null) ?  pageNumber : 0;
                int size = (pageSize != null) ? pageSize : Integer.MAX_VALUE;
                int totalElements;
                Pageable pageable = size == Integer.MAX_VALUE ? Pageable.unpaged() : PageRequest.of(page - 1, size);
                List<Long> ids;

                if (size == Integer.MAX_VALUE) {
                        ids = categoryRepository.findAllCategoryIds();
                        totalElements = ids.size();
                } else {
                        Page<Long> idPage = categoryRepository.findAllCategoryIds(pageable);
                        ids = idPage.getContent();
                        totalElements = (int) idPage.getTotalElements();
                }

                return new PaginateObject<>(totalElements, ids);
        }

        public Category findById(Long id) {
                return findCategoryById(id);
        }

        public Category findCategoryById(Long id) {
                return categoryRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.NOT_FOUND.getMessage("Categoria")));
        }
}




