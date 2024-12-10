package com.br.delogic.services;

import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.User;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.UserRepository;
import com.br.delogic.service.impl.UserServiceImpl;
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

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenUserExists() {
        Long userId = 1L;
        User mockUser = User.builder()
                .userId(userId)
                .username("LeandroHsn")
                .firstName("Leandro")
                .lastName("Nunes")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(mockUser.getUserId(), result.getUserId());
        assertEquals(mockUser.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findById_whenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.findById(userId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(ErrorMessages.NOT_FOUND.getMessage("Usuário"), exception.getReason());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findIds_whenPaginationNotProvided() {
        List<Long> mockIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        when(userRepository.findAllIds()).thenReturn(mockIds);

        PaginateObject<Long> result = userService.findIds(null, null);

        assertNotNull(result);
        assertEquals(mockIds.size(), result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(userRepository, times(1)).findAllIds();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findIds_whenPaginationProvided() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Long> mockIds = Arrays.asList(1L, 2L);
        Page<Long> mockPage = new PageImpl<>(mockIds, pageable, 5);
        when(userRepository.findAllIds(pageable)).thenReturn(mockPage);

        PaginateObject<Long> result = userService.findIds(pageNumber + 1, pageSize);

        assertNotNull(result);
        assertEquals(5, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(userRepository, times(1)).findAllIds(pageable);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findAll_whenPaginationNotProvided() {
        List<User> mockUsers = Arrays.asList(
                User.builder()
                        .userId(1L)
                        .username("LeandroHsn")
                        .firstName("Leandro")
                        .lastName("Nunes")
                        .build(),
                User.builder()
                        .userId(2L)
                        .username("Felipes")
                        .firstName("Felipe")
                        .lastName("Nunes")
                        .build()
        );
        when(userRepository.findAll()).thenReturn(mockUsers);

        PaginateObject<User> result = userService.findAll(null, null);

        assertNotNull(result);
        assertEquals(mockUsers.size(), result.getSize());
        assertEquals(mockUsers, result.getTable());
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findAll_whenPaginationProvided() {
        int pageNumber = 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<User> mockUsers = Arrays.asList(
                User.builder()
                        .userId(1L)
                        .username("LeandroHsn")
                        .firstName("Leandro")
                        .lastName("Nunes")
                        .build()
        );
        Page<User> mockPage = new PageImpl<>(mockUsers, pageable, 2);
        when(userRepository.findAll(pageable)).thenReturn(mockPage);

        PaginateObject<User> result = userService.findAll(pageNumber, pageSize);

        assertNotNull(result);
        assertEquals(2, result.getSize());
        assertEquals(mockUsers, result.getTable());
        verify(userRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(userRepository);
    }

}

