package com.br.delogic.controller;

import com.br.delogic.model.Date;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/date")
public class DateController {

    @Autowired
    private DateService dateService;

    @GetMapping(value = "/all")
    public ResponseEntity<PaginateObject<Date>> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(dateService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/all/ids")
    public ResponseEntity<PaginateObject<Long>> findAllId(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(dateService.findIds(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Date> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(dateService.findById(id), HttpStatus.OK);
    }
}
