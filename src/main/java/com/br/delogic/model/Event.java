package com.br.delogic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private Date date;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_start_time")
    private String eventStartTime;

}
