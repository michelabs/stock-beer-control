package com.dio.api.beerstock.dto;

import com.dio.api.beerstock.enums.BeerType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    public String name;

    @NotNull
    @Size(min = 1, max = 200)
    public String brand;

    @NotNull
    @Max(500)
    public Integer max;

    @NotNull
    @Max(100)
    public Integer quantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    public BeerType type;
}
