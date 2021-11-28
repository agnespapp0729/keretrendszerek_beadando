package hu.uni.eku.tzs.model;

import hu.uni.eku.tzs.enums.IsEnglish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {

    private int movieId;

    private int year;

    private IsEnglish isEnglish;

    private String country;

    private int runningTime;

}
