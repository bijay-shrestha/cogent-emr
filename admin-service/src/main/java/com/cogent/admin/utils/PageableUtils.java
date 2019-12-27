package com.cogent.admin.utils;

import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import java.util.Objects;
import java.util.function.BiConsumer;

public class PageableUtils {

    public static BiConsumer<Pageable, Query> addPagination = (pageable, query) -> {
        if (!Objects.isNull(pageable) && pageable.getPageNumber() != 0) {
            query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }
    };
}
