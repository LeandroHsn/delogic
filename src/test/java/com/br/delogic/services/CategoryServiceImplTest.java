package com.br.delogic.services;

import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Category;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.CategoryRepository;
import com.br.delogic.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenCategoryExists() {
        Long categoryId = 1L;
        Category mockCategory = Category.builder()
                .categoryId(categoryId)
                .build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        Category result = categoryService.findById(categoryId);

        assertNotNull(result);
        assertEquals(mockCategory.getCategoryId(), result.getCategoryId());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void findById_whenCategoryDoesNotExist() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            categoryService.findById(categoryId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(ErrorMessages.NOT_FOUND.getMessage("Categoria"), exception.getReason());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void findIds_whenPaginationNotProvided() {
        List<Long> mockIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        when(categoryRepository.findAllCategoryIds()).thenReturn(mockIds);

        PaginateObject<Long> result = categoryService.findIds(null, null);

        assertNotNull(result);
        assertEquals(mockIds.size(), result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(categoryRepository, times(1)).findAllCategoryIds();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void findIds_whenPaginationProvided() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Long> mockIds = Arrays.asList(1L, 2L);
        Page<Long> mockPage = new PageImpl<>(mockIds, pageable, 5);
        when(categoryRepository.findAllCategoryIds(pageable)).thenReturn(mockPage);

        PaginateObject<Long> result = categoryService.findIds(pageNumber + 1, pageSize);

        assertNotNull(result);
        assertEquals(5, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(categoryRepository, times(1)).findAllCategoryIds(pageable);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void findAll_whenPaginationNotProvided() {
        List<Category> mockCategory = Arrays.asList(
                Category.builder()
                        .categoryId(1L)
                        .build(),
                Category.builder()
                        .categoryId(2L)
                        .build()
        );
        when(categoryRepository.findAll()).thenReturn(mockCategory);

        PaginateObject<Category> result = categoryService.findAll(null, null);

        assertNotNull(result);
        assertEquals(mockCategory.size(), result.getSize());
        assertEquals(mockCategory, result.getTable());
        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void findAll_whenPaginationProvided() {
        int pageNumber = 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Category> mockCategory = Arrays.asList(
                Category.builder()
                        .categoryId(1L)
                        .build()
        );
        Page<Category> mockPage = new PageImpl<>(mockCategory, pageable, 2);
        when(categoryRepository.findAll(pageable)).thenReturn(mockPage);

        PaginateObject<Category> result = categoryService.findAll(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getSize());
        assertEquals(mockCategory, result.getTable());
        verify(categoryRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(categoryRepository);
    }

}

