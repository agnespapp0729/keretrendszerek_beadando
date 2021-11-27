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
public class UserDto {

    private int userId;

    @NotBlank(message="The age of the user mustn't be empty")
    private int userAge;

    @NotBlank(message="The gender of the user mustn't be empty")
    private int userGender;

    @NotBlank(message="The occupation mustn't be empty")
    private int occupation;


}
