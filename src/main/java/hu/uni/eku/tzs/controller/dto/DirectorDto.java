package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto {

    private int directorId;

    @NotBlank(message="The quality mustn't be empty")
    private int directorQuality;

    @NotBlank(message="The revenue mustn't be empty")
    private int revenue;

}
