package com.polezhaiev.shop.repository.book.spec;

import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "author";
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                Arrays.stream(params)
                        .map(a -> criteriaBuilder.like(root.get("author"), "%" + a + "%"))
                        .reduce(criteriaBuilder::or)
                        .orElse(null);
    }
}
