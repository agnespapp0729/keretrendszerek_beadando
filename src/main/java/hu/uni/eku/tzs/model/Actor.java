package hu.uni.eku.tzs.model;

import hu.uni.eku.tzs.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Actor {
    private int actorId;

    private Gender actorGender;

    private int actorQuality;

}
