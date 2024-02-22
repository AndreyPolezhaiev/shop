package com.polezhaiev.shop.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.ISBN;

@Data
@Accessors(chain = true)
public class CreateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @ISBN(type = ISBN.Type.ISBN_10)
    private String isbn;
    @Min(0)
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    private String coverImage;
    @NotNull
    private Set<Long> categoryIds;
}
