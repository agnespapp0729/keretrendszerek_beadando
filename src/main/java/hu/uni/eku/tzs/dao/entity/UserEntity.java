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
@Entity(name = "users")
public class UserEntity {

    @Id
    @Column(name = "userid")
    private int userId;

    @Column(name = "age")
    private String age;

    @Column(name = "u_gender")
    private String userGender;

    @Column(name = "occupation")
    private String occupation;

}
