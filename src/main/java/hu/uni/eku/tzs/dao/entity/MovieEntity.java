package hu.uni.eku.tzs.dao.entity;

import hu.uni.eku.tzs.enums.Gender;
import hu.uni.eku.tzs.enums.IsEnglish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="movies")
public class MovieEntity {

    @Id
    @Column(name="movieId")
    private int movieId;

    @Column(name="year")
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(name="isEnglish")
    private IsEnglish isEnglish;

    @Column(name="country")
    private String country;

    @Column(name="runningtime")
    private int runningTime;


}
