package org.example.cinemaflix.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardDetailDto {

    @NotNull
    private String customerFullName;

    @NotNull
    @Max(value = 16)
    @Min(value = 16)
    private String bankAccount;

    @NotNull
    private Integer cvv;

    @NotNull
    private LocalDate expiryDate;
}
