package com.br.delogic.controller;

import com.br.delogic.model.Venue;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping(value = "/all")
    public ResponseEntity<PaginateObject<Venue>> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(venueService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/all/ids")
    public ResponseEntity<PaginateObject<Long>> findAllId(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(venueService.findIds(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Venue> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(venueService.findById(id), HttpStatus.OK);
    }
}
