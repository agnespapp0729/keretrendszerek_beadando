package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.enums.IsEnglish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private int movieId;

    @NotBlank(message = "The year cannot be empty")
    private int year;

    @NotBlank(message = "IsEnglish cannot be empty")
    private IsEnglish isEnglish;

    @NotBlank(message = "The country cannot be empty")
    private String country;

    @NotBlank(message = "The running time cannot be empty")
    private int runningTime;
}
