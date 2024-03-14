package springbook.user.test;

import org.springframework.stereotype.Component;

@Component
// @Component("electricMotorBean") // id를 직접 명시 할때
public class ElectricMotor implements PowerUnit {

  @Override
  public void printPowerType() {
    System.out.println("ElectricMotor");
  }
}