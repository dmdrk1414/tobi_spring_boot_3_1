package springbook.user.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//https://citronbanana.tistory.com/112
@Component
// @Component("carBean") // id를 직접 입력할 때
public class Car {

  private final PowerUnit powerUnit; // Dependency
  private final String color;

  // Component을 찾는다.
  //    @Autowired는 의존 관계를 맺어주는(의존성을 주입해주는) 어노테이션이다.
  //    xml 형식에서 <property> 태그와 ref 속성을 이용했던
  //    의존 관계 설정을 @Autowired로 대체하는 것이다.
  @Autowired
  public Car(PowerUnit powerUnit, @Value("red") String color) {
    this.powerUnit = powerUnit; // 생성자를 통한 외부 주입(Injection)
    this.color = color;
  }

  public void sound() {
    System.out.println("booooo~~~");
  }

  public String color() {
    return this.color;
  }

  public void powerType() {
    this.powerUnit.printPowerType();
  }
}