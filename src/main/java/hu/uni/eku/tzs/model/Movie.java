package hu.uni.eku.tzs.model;

import hu.uni.eku.tzs.IsEnglish;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    private int movieId;

    private IsEnglish isEnglish;

    private String country;

    private int runningTime;

}
