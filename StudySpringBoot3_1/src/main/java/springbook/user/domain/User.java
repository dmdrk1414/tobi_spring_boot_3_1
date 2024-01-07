package springbook.user.domain;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String id;
    String name;
    String password;
}
