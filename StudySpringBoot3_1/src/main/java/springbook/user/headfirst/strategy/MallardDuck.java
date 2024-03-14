package springbook.user.headfirst.strategy;

import springbook.user.headfirst.strategy.fly.FlyWithWings;
import springbook.user.headfirst.strategy.quack.Quack;

public class MallardDuck extends Duck {

  public MallardDuck() {
    super.quackBehavior = new Quack();
    super.flyBehavior = new FlyWithWings();
  }

  @Override
  public void display() {
    System.out.println("저는 물오리입니다.");
  }
}
