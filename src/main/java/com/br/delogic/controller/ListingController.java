package com.br.delogic.controller;

import com.br.delogic.model.Listing;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @GetMapping(value = "/all")
    public ResponseEntity<PaginateObject<Listing>> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(listingService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/all/ids")
    public ResponseEntity<PaginateObject<Long>> findAllId(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(listingService.findIds(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Listing> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(listingService.findById(id), HttpStatus.OK);
    }
}
