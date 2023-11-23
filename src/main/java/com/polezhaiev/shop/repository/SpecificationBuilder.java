package com.polezhaiev.shop.repository;

import com.polezhaiev.shop.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder <T> {
    Specification<T> build(BookSearchParametersDto searchParameters);
}
