package hu.uni.eku.tzs.dao.entity;

import hu.uni.eku.tzs.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="actors")
public class ActorEntity {

    @Id
    @Column(name="actorId")
    private int actorId;

    @Enumerated(EnumType.STRING)
    @Column(name="a_gender")
    private Gender actorGender;

    @Column(name="a_quality")
    private int actorQuality;

}
