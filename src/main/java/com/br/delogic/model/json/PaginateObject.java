package com.br.delogic.model.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginateObject<T> {
    private Integer size = 0;
    private List<T> table = new ArrayList<>();

    public static <T> PaginateObject<T> of(List<T> list) {
        return new PaginateObject<>(list.size(), list);
    }

    public static <T> PaginateObject<T> of(int size, List<T> list) {
        return new PaginateObject<>(size, list);
    }

    public static <T> PaginateObject<T> of(Set<T> set) {
        return new PaginateObject<>(set.size(), new ArrayList<>(set));
    }

}
