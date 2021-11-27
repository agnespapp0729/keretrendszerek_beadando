package hu.uni.eku.tzs.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int userId;

    private String age;

    private String userGender;

    private String occupation;

}
