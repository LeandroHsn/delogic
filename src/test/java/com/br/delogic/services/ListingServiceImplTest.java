package com.br.delogic.services;

import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Listing;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.ListingRepository;
import com.br.delogic.service.impl.ListingServiceImpl;
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

class ListingServiceImplTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingServiceImpl listingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenListingExists() {
        Long listingId = 1L;
        Listing mockListing = Listing.builder()
                .listingId(listingId)
                .build();

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(mockListing));

        Listing result = listingService.findById(listingId);

        assertNotNull(result);
        assertEquals(mockListing.getListingId(), result.getListingId());
        verify(listingRepository, times(1)).findById(listingId);
    }

    @Test
    void findById_whenListingDoesNotExist() {
        Long listingId = 1L;
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            listingService.findById(listingId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(ErrorMessages.NOT_FOUND.getMessage("Listagem"), exception.getReason());
        verify(listingRepository, times(1)).findById(listingId);
    }

    @Test
    void findIds_whenPaginationNotProvided() {
        List<Long> mockIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        when(listingRepository.findAllIds()).thenReturn(mockIds);

        PaginateObject<Long> result = listingService.findIds(null, null);

        assertNotNull(result);
        assertEquals(mockIds.size(), result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(listingRepository, times(1)).findAllIds();
        verifyNoMoreInteractions(listingRepository);
    }

    @Test
    void findIds_whenPaginationProvided() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Long> mockIds = Arrays.asList(1L, 2L);
        Page<Long> mockPage = new PageImpl<>(mockIds, pageable, 5);
        when(listingRepository.findAllIds(pageable)).thenReturn(mockPage);

        PaginateObject<Long> result = listingService.findIds(pageNumber + 1, pageSize);

        assertNotNull(result);
        assertEquals(5, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(listingRepository, times(1)).findAllIds(pageable);
        verifyNoMoreInteractions(listingRepository);
    }

    @Test
    void findAll_whenPaginationNotProvided() {
        List<Listing> mockListings = Arrays.asList(
                Listing.builder()
                        .listingId(1L)
                        .build(),
                Listing.builder()
                        .listingId(2L)
                        .build()
        );
        when(listingRepository.findAll()).thenReturn(mockListings);

        PaginateObject<Listing> result = listingService.findAll(null, null);

        assertNotNull(result);
        assertEquals(mockListings.size(), result.getSize());
        assertEquals(mockListings, result.getTable());
        verify(listingRepository, times(1)).findAll();
        verifyNoMoreInteractions(listingRepository);
    }

    @Test
    void findAll_whenPaginationProvided() {
        int pageNumber = 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Listing> mockListings = Arrays.asList(
                Listing.builder()
                        .listingId(1L)
                        .build()
        );
        Page<Listing> mockPage = new PageImpl<>(mockListings, pageable, 2);
        when(listingRepository.findAll(pageable)).thenReturn(mockPage);

        PaginateObject<Listing> result = listingService.findAll(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getSize());
        assertEquals(mockListings, result.getTable());
        verify(listingRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(listingRepository);
    }

}

