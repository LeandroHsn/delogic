package com.br.delogic.services;

import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Date;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.DateRepository;
import com.br.delogic.service.impl.DateServiceImpl;
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

class DataServiceImplTest {

    @Mock
    private DateRepository dateRepository;

    @InjectMocks
    private DateServiceImpl dateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenDateExists() {
        Long dateId = 1L;
        Date mockDate = Date.builder()
                .id(dateId)
                .build();

        when(dateRepository.findById(dateId)).thenReturn(Optional.of(mockDate));

        Date result = dateService.findById(dateId);

        assertNotNull(result);
        assertEquals(mockDate.getId(), result.getId());
        verify(dateRepository, times(1)).findById(dateId);
    }

    @Test
    void findById_whenDateDoesNotExist() {
        Long dateId = 1L;
        when(dateRepository.findById(dateId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            dateService.findById(dateId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(ErrorMessages.NOT_FOUND.getMessage("Date"), exception.getReason());
        verify(dateRepository, times(1)).findById(dateId);
    }

    @Test
    void findIds_whenPaginationNotProvided() {
        List<Long> mockIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        when(dateRepository.findAllIds()).thenReturn(mockIds);

        PaginateObject<Long> result = dateService.findIds(null, null);

        assertNotNull(result);
        assertEquals(mockIds.size(), result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(dateRepository, times(1)).findAllIds();
        verifyNoMoreInteractions(dateRepository);
    }

    @Test
    void findIds_whenPaginationProvided() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Long> mockIds = Arrays.asList(1L, 2L);
        Page<Long> mockPage = new PageImpl<>(mockIds, pageable, 5);
        when(dateRepository.findAllIds(pageable)).thenReturn(mockPage);

        PaginateObject<Long> result = dateService.findIds(pageNumber + 1, pageSize);

        assertNotNull(result);
        assertEquals(5, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(dateRepository, times(1)).findAllIds(pageable);
        verifyNoMoreInteractions(dateRepository);
    }

    @Test
    void findAll_whenPaginationNotProvided() {
        List<Date> mockDates = Arrays.asList(
                Date.builder()
                        .id(1L)
                        .build(),
                Date.builder()
                        .id(2L)
                        .build()
        );
        when(dateRepository.findAll()).thenReturn(mockDates);

        PaginateObject<Date> result = dateService.findAll(null, null);

        assertNotNull(result);
        assertEquals(mockDates.size(), result.getSize());
        assertEquals(mockDates, result.getTable());
        verify(dateRepository, times(1)).findAll();
        verifyNoMoreInteractions(dateRepository);
    }

    @Test
    void findAll_whenPaginationProvided() {
        int pageNumber = 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Date> mockDates = Arrays.asList(
                Date.builder()
                        .id(1L)
                        .build()
        );
        Page<Date> mockPage = new PageImpl<>(mockDates, pageable, 2);
        when(dateRepository.findAll(pageable)).thenReturn(mockPage);

        PaginateObject<Date> result = dateService.findAll(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getSize());
        assertEquals(mockDates, result.getTable());
        verify(dateRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(dateRepository);
    }

}

