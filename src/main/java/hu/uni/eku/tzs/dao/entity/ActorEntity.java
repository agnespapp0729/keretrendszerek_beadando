package hu.uni.eku.tzs.dao.entity;

import hu.uni.eku.tzs.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "actors")
public class ActorEntity {

    @Id
    @Column(name = "actorid")
    private int actorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "a_gender")
    private Gender actorGender;

    @Column(name = "a_quality")
    private int actorQuality;

}
