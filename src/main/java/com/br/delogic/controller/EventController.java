package com.br.delogic.controller;

import com.br.delogic.model.Event;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/all")
    public ResponseEntity<PaginateObject<Event>> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(eventService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/all/ids")
    public ResponseEntity<PaginateObject<Long>> findAllId(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(eventService.findIds(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(eventService.findById(id), HttpStatus.OK);
    }
}
