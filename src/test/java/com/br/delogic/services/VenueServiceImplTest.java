package com.br.delogic.services;

import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Venue;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.VenueRepository;
import com.br.delogic.service.impl.VenueServiceImpl;
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

class VenueServiceImplTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueServiceImpl venueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenVenueExists() {
        Long venueId = 1L;
        Venue mockVenue = Venue.builder()
                .venueId(venueId)
                .build();

        when(venueRepository.findById(venueId)).thenReturn(Optional.of(mockVenue));

        Venue result = venueService.findById(venueId);

        assertNotNull(result);
        assertEquals(mockVenue.getVenueId(), result.getVenueId());
        verify(venueRepository, times(1)).findById(venueId);
    }

    @Test
    void findById_whenVenueDoesNotExist() {
        Long VenueId = 1L;
        when(venueRepository.findById(VenueId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            venueService.findById(VenueId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(ErrorMessages.NOT_FOUND.getMessage("Local"), exception.getReason());
        verify(venueRepository, times(1)).findById(VenueId);
    }

    @Test
    void findIds_whenPaginationNotProvided() {
        List<Long> mockIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        when(venueRepository.findAllIds()).thenReturn(mockIds);

        PaginateObject<Long> result = venueService.findIds(null, null);

        assertNotNull(result);
        assertEquals(mockIds.size(), result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(venueRepository, times(1)).findAllIds();
        verifyNoMoreInteractions(venueRepository);
    }

    @Test
    void findIds_whenPaginationProvided() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Long> mockIds = Arrays.asList(1L, 2L);
        Page<Long> mockPage = new PageImpl<>(mockIds, pageable, 5);
        when(venueRepository.findAllIds(pageable)).thenReturn(mockPage);

        PaginateObject<Long> result = venueService.findIds(pageNumber + 1, pageSize);

        assertNotNull(result);
        assertEquals(5, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(venueRepository, times(1)).findAllIds(pageable);
        verifyNoMoreInteractions(venueRepository);
    }

    @Test
    void findAll_whenPaginationNotProvided() {
        List<Venue> mockVenues = Arrays.asList(
                Venue.builder()
                        .venueId(1L)
                        .build(),
                Venue.builder()
                        .venueId(2L)
                        .build()
        );
        when(venueRepository.findAll()).thenReturn(mockVenues);

        PaginateObject<Venue> result = venueService.findAll(null, null);

        assertNotNull(result);
        assertEquals(mockVenues.size(), result.getSize());
        assertEquals(mockVenues, result.getTable());
        verify(venueRepository, times(1)).findAll();
        verifyNoMoreInteractions(venueRepository);
    }

    @Test
    void findAll_whenPaginationProvided() {
        int pageNumber = 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Venue> mockVenues = Arrays.asList(
                Venue.builder()
                        .venueId(1L)
                        .build()
        );
        Page<Venue> mockPage = new PageImpl<>(mockVenues, pageable, 2);
        when(venueRepository.findAll(pageable)).thenReturn(mockPage);

        PaginateObject<Venue> result = venueService.findAll(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getSize());
        assertEquals(mockVenues, result.getTable());
        verify(venueRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(venueRepository);
    }

}

