package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "directors")
public class DirectorEntity {
    @Id
    @Column(name = "directorid")
    private int directorId;

    @Column(name = "d_quality")
    private int directorQuality;

    @Column(name = "avg_revenue")
    private int averageRevenue;
}
