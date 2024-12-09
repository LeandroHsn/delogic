package com.br.delogic.service.impl;
import com.br.delogic.enums.ErrorMessages;
import com.br.delogic.model.User;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.repository.UserRepository;
import com.br.delogic.service.UserService;
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
public class UserServiceImpl implements UserService {

        @Autowired
        private UserRepository userRepository;

        public PaginateObject<User> findAll(Integer pageNumber, Integer pageSize) {
                return findAllElementPageable(pageNumber, pageSize);
        }

        @Transactional(readOnly = true)
        public PaginateObject<User> findAllElementPageable(Integer pageNumber, Integer pageSize) {

                Pageable pageable;
                List<User> listUsers;
                int size;

                if (Objects.nonNull(pageNumber) && pageNumber > 0 && Objects.nonNull(pageSize)) {
                        pageable = PageRequest.of(pageNumber - 1, pageSize);
                        Page<User> list = userRepository.findAll(pageable);
                        listUsers = new ArrayList<>(list.getContent());
                        size = (int) list.getTotalElements();
                } else {
                        listUsers = userRepository.findAll();
                        size = listUsers.size();
                }

                return new PaginateObject<>(size, listUsers);
        }

        public PaginateObject<Long> findIds(Integer pageNumber, Integer pageSize) {

                int page = (pageNumber != null) ? pageNumber : 0;
                int size = (pageSize != null) ? pageSize : Integer.MAX_VALUE;
                int totalElements;
                Pageable pageable = size == Integer.MAX_VALUE ? Pageable.unpaged() : PageRequest.of(page, size);
                List<Long> ids;

                if (size == Integer.MAX_VALUE) {
                        ids = userRepository.findAllIds();
                        totalElements = ids.size();
                } else {
                        Page<Long> idPage = userRepository.findAllIds(pageable);
                        ids = idPage.getContent();
                        totalElements = (int) idPage.getTotalElements();
                }

                return new PaginateObject<>(totalElements, ids);
        }

        public User findById(Long id) {
                return findUserById(id);
        }

        public User findUserById(Long id) {
                return userRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.NOT_FOUND.getMessage("Usuário")));
        }
}




