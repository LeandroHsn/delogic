package com.br.delogic.services;

import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.Event;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.EventRepository;
import com.br.delogic.service.impl.EventServiceImpl;
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

class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenEventExists() {
        Long eventId = 1L;
        Event mockEvent = Event.builder()
                .eventId(eventId)
                .build();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));

        Event result = eventService.findById(eventId);

        assertNotNull(result);
        assertEquals(mockEvent.getEventId(), result.getEventId());
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void findById_whenEventDoesNotExist() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            eventService.findById(eventId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(ErrorMessages.NOT_FOUND.getMessage("Event"), exception.getReason());
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void findIds_whenPaginationNotProvided() {
        List<Long> mockIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        when(eventRepository.findAllIds()).thenReturn(mockIds);

        PaginateObject<Long> result = eventService.findIds(null, null);

        assertNotNull(result);
        assertEquals(mockIds.size(), result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(eventRepository, times(1)).findAllIds();
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void findIds_whenPaginationProvided() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Long> mockIds = Arrays.asList(1L, 2L);
        Page<Long> mockPage = new PageImpl<>(mockIds, pageable, 5);
        when(eventRepository.findAllIds(pageable)).thenReturn(mockPage);

        PaginateObject<Long> result = eventService.findIds(pageNumber + 1, pageSize);

        assertNotNull(result);
        assertEquals(5, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(eventRepository, times(1)).findAllIds(pageable);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void findAll_whenPaginationNotProvided() {
        List<Event> mockEvents = Arrays.asList(
                Event.builder()
                        .eventId(1L)
                        .build(),
                Event.builder()
                        .eventId(2L)
                        .build()
        );
        when(eventRepository.findAll()).thenReturn(mockEvents);

        PaginateObject<Event> result = eventService.findAll(null, null);

        assertNotNull(result);
        assertEquals(mockEvents.size(), result.getSize());
        assertEquals(mockEvents, result.getTable());
        verify(eventRepository, times(1)).findAll();
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void findAll_whenPaginationProvided() {
        int pageNumber = 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Event> mockEvents = Arrays.asList(
                Event.builder()
                        .eventId(1L)
                        .build()
        );
        Page<Event> mockPage = new PageImpl<>(mockEvents, pageable, 2);
        when(eventRepository.findAll(pageable)).thenReturn(mockPage);

        PaginateObject<Event> result = eventService.findAll(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getSize());
        assertEquals(mockEvents, result.getTable());
        verify(eventRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(eventRepository);
    }

}

