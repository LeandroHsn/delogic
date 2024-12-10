package com.br.delogic.controller;

import com.br.delogic.model.Sale;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping(value = "/all")
    public ResponseEntity<PaginateObject<Sale>> findAll(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(saleService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/all/ids")
    public ResponseEntity<PaginateObject<Long>> findAllId(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return new ResponseEntity<>(saleService.findIds(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Sale> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(saleService.findById(id), HttpStatus.OK);
    }
}
