package com.br.delogic.controller;

import com.br.delogic.model.User;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<PaginateObject<User>> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(userService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/all/ids")
    public ResponseEntity<PaginateObject<Long>> findAllId(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(userService.findIds(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }
}
