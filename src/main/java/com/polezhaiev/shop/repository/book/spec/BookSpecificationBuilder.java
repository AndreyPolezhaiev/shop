package com.polezhaiev.shop.repository.book.spec;

import com.polezhaiev.shop.dto.BookSearchParametersDto;
import com.polezhaiev.shop.model.Book;
import com.polezhaiev.shop.repository.SpecificationBuilder;
import com.polezhaiev.shop.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> phoneSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(phoneSpecificationProviderManager.getSpecification("author")
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(phoneSpecificationProviderManager.getSpecification("title")
                    .getSpecification(searchParameters.titles()));
        }
        return spec;
    }
}
