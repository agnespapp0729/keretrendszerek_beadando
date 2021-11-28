package hu.uni.eku.tzs.model;

import hu.uni.eku.tzs.enums.Gender;
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
public class Actor {

    private int actorId;

    private Gender actorGender;

    private int actorQuality;

}
