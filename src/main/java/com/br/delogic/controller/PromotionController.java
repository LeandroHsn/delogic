package com.br.delogic.controller;

import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.model.json.PromotionFilter;
import com.br.delogic.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // Preciso melhorar a lógica
    @GetMapping(value = "/withPredicate")
    public ResponseEntity<PaginateObject<Long>> findAllWithPredicate(
            @RequestParam(value = "contextDate", required = true) LocalDate contextDate,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "eventCity", required = false) String eventCity,
            @RequestParam(value = "numberOfTickets", required = false, defaultValue = "0") String numberOfTickets,
            @RequestParam(value = "orderBy", required = false, defaultValue = "listingTimestamp,desc") String orderBy){

        PromotionFilter filter = PromotionFilter.builder()
                .contextDate(contextDate)
                .categoryId(categoryId)
                .eventCity(eventCity)
                .numberOfTickets(numberOfTickets)
                .orderBy(orderBy)
                .build();

        return new ResponseEntity<>(promotionService.findPromotion(filter), HttpStatus.OK);
    }

    @GetMapping(value = "/withoutPredicate")
    public ResponseEntity<PaginateObject<Long>> findAllWithoutPredicate(
            @RequestParam(value = "contextDate", required = true) LocalDate contextDate,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "eventCity", required = false) String eventCity){

        PromotionFilter filter = PromotionFilter.builder()
                .contextDate(contextDate)
                .categoryId(categoryId)
                .eventCity(eventCity)
                .build();

        return new ResponseEntity<>(promotionService.findPromotion(filter, true), HttpStatus.OK);
    }
}
