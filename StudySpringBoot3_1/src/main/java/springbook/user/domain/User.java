package springbook.user.domain;

import lombok.*;

import java.util.List;
import springbook.user.constant.Level;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  String id;
  String name;
  String password;
  Level level;
  int login;
  int recommend;
}
