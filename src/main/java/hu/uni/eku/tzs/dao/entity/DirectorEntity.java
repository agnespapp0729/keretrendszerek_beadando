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
@Entity(name="directors")
public class DirectorEntity {
    @Id
    @Column(name="directorId")
    private int directorId;

    @Column(name="d_quality")
    private int directorQuality;

    @Column(name="avg_revenue")
    private int averageRevenue;
}
