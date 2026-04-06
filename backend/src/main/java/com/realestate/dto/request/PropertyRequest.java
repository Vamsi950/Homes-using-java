package com.realestate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PropertyRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private BigDecimal price;

    private String propertyType;
    private String location;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer areaSqft;
}
