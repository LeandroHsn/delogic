package com.br.delogic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listing_id")
    private int listingId;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private Date date;

    @Column(name = "number_of_tickets")
    private int numberOfTickets;

    @Column(name = "price_per_ticket")
    private BigDecimal pricePerTicket;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "listing_timestamp")
    private LocalDateTime listingTimestamp;

}
