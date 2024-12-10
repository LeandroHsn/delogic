package com.br.delogic.services;

import com.br.delogic.model.Listing;
import com.br.delogic.model.json.PaginateObject;
import com.br.delogic.model.json.PromotionFilter;
import com.br.delogic.repository.ListingRepository;
import com.br.delogic.service.impl.PromotionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PromotionServiceImplTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPromotionWithPredicate() {
        PromotionFilter filter = PromotionFilter.builder()
                .contextDate(LocalDate.of(2024, 12, 15))
                .categoryId(1L)
                .eventCity("Ituiutaba")
                .build();

        List<Listing> mockListings = Arrays.asList(
                Listing.builder().listingId(1L).build(), Listing.builder().listingId(2L).build(), Listing.builder().listingId(3L).build()
        );

        when(listingRepository.findAll(filter)).thenReturn(mockListings);

        PaginateObject<Long> result = promotionService.findPromotion(filter);

        assertEquals(3, result.getSize());
        assertEquals(Arrays.asList(1L, 2L, 3L), result.getTable());
        verify(listingRepository, times(1)).findAll(filter);
    }

    @Test
    void testFindPromotionWithoutPredicate() {
        PromotionFilter filter = PromotionFilter.builder()
                .contextDate(LocalDate.of(2024, 12, 15))
                .categoryId(2L)
                .eventCity("Ituiutaba")
                .build();

        List<Long> mockIds = Arrays.asList(10L, 20L, 30L);

        when(listingRepository.findListingsByFilters(
                filter.getContextDate(),
                filter.getCategoryId(),
                filter.getEventCity(),
                10
        )).thenReturn(mockIds);

        PaginateObject<Long> result = promotionService.findPromotion(filter, true);

        assertEquals(3, result.getSize());
        assertEquals(mockIds, result.getTable());
        verify(listingRepository, times(1)).findListingsByFilters(
                filter.getContextDate(),
                filter.getCategoryId(),
                filter.getEventCity(),
                10
        );
    }

    @Test
    void testFindPromotionEmptyResult() {
        PromotionFilter filter = PromotionFilter.builder()
                .contextDate(LocalDate.of(2024, 12, 15))
                .build();

        when(listingRepository.findAll(filter)).thenReturn(Collections.emptyList());

        PaginateObject<Long> result = promotionService.findPromotion(filter);

        assertEquals(0, result.getSize());
        assertEquals(Collections.emptyList(), result.getTable());
        verify(listingRepository, times(1)).findAll(filter);
    }
}
