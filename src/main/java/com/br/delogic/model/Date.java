package com.br.delogic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dates")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "calendar_date")
    private LocalDate calendarDate;

    @Column(name = "day_date")
    private String day;

    @Column(name = "week_date")
    private int week;

    @Column(name = "month_date")
    private String month;

    @Column(name = "quarter_date")
    private int quarter;

    @Column(name = "year_date")
    private int year;

    @Column(name = "holiday_flag")
    private String holidayFlag;

}
