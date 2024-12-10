package com.br.delogic.model.json;

import com.br.delogic.model.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionFilter implements Specification<Listing> {

    private LocalDate contextDate;

    private Long categoryId;

    private String eventCity;

    private String orderBy;

    private String numberOfTickets;

    @Override
    public Predicate toPredicate(Root<Listing> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(contextDate)) {
            predicates.add(cb.equal(root.get("date").get("calendarDate"), contextDate));
        }

        if (Objects.nonNull(categoryId)) {
            predicates.add(cb.equal(root.get("event").get("category").get("categoryId"), categoryId));
        }

        if (Objects.nonNull(eventCity)) {
            predicates.add(cb.equal(root.get("event").get("venue").get("city"), eventCity));
        }

        if (Objects.nonNull(numberOfTickets)) {
            predicates.add(cb.greaterThan(root.get("numberOfTickets"), Integer.parseInt(numberOfTickets)));
        }

        if (Objects.nonNull(orderBy) && !orderBy.equals("asc")) {
            String[] orders = orderBy.split(";");
            for (Object order : orders) {
                String cbOrder = order.toString();
                String[] cbOrders = cbOrder.split(",");
                if (cbOrders[1].equals("asc")) {
                    query.orderBy(cb.asc(root.get(cbOrders[0])));
                } else {
                    query.orderBy(cb.desc(root.get(cbOrders[0])));
                }
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
