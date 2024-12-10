package com.br.delogic.services;

import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Sale;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.SaleRepository;
import com.br.delogic.service.impl.SaleServiceImpl;
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

class SaleServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleServiceImpl saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenSaleExists() {
        Long saleId = 1L;
        Sale mockSale = Sale.builder()
                .saleId(saleId)
                .build();

        when(saleRepository.findById(saleId)).thenReturn(Optional.of(mockSale));

        Sale result = saleService.findById(saleId);

        assertNotNull(result);
        assertEquals(mockSale.getSaleId(), result.getSaleId());
        verify(saleRepository, times(1)).findById(saleId);
    }

    @Test
    void findById_whenSaleDoesNotExist() {
        Long SaleId = 1L;
        when(saleRepository.findById(SaleId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            saleService.findById(SaleId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(ErrorMessages.NOT_FOUND.getMessage("Venda"), exception.getReason());
        verify(saleRepository, times(1)).findById(SaleId);
    }

    @Test
    void findIds_whenPaginationNotProvided() {
        List<Long> mockIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        when(saleRepository.findAllIds()).thenReturn(mockIds);

        PaginateObject<Long> result = saleService.findIds(null, null);

        assertNotNull(result);
        assertEquals(mockIds.size(), result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(saleRepository, times(1)).findAllIds();
        verifyNoMoreInteractions(saleRepository);
    }

    @Test
    void findIds_whenPaginationProvided() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Long> mockIds = Arrays.asList(1L, 2L);
        Page<Long> mockPage = new PageImpl<>(mockIds, pageable, 5);
        when(saleRepository.findAllIds(pageable)).thenReturn(mockPage);

        PaginateObject<Long> result = saleService.findIds(pageNumber + 1, pageSize);

        assertNotNull(result);
        assertEquals(5, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(saleRepository, times(1)).findAllIds(pageable);
        verifyNoMoreInteractions(saleRepository);
    }

    @Test
    void findAll_whenPaginationNotProvided() {
        List<Sale> mockSales = Arrays.asList(
                Sale.builder()
                        .saleId(1L)
                        .build(),
                Sale.builder()
                        .saleId(2L)
                        .build()
        );
        when(saleRepository.findAll()).thenReturn(mockSales);

        PaginateObject<Sale> result = saleService.findAll(null, null);

        assertNotNull(result);
        assertEquals(mockSales.size(), result.getSize());
        assertEquals(mockSales, result.getTable());
        verify(saleRepository, times(1)).findAll();
        verifyNoMoreInteractions(saleRepository);
    }

    @Test
    void findAll_whenPaginationProvided() {
        int pageNumber = 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Sale> mockSales = Arrays.asList(
                Sale.builder()
                        .saleId(1L)
                        .build()
        );
        Page<Sale> mockPage = new PageImpl<>(mockSales, pageable, 2);
        when(saleRepository.findAll(pageable)).thenReturn(mockPage);

        PaginateObject<Sale> result = saleService.findAll(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getSize());
        assertEquals(mockSales, result.getTable());
        verify(saleRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(saleRepository);
    }

}

