package springbook.user.headfirst.strategy;

import springbook.user.headfirst.strategy.fly.FlyBehavior;
import springbook.user.headfirst.strategy.quack.QuackBehavior;

public abstract class Duck {

  FlyBehavior flyBehavior;
  QuackBehavior quackBehavior;

  public Duck() {
  }

  public abstract void display();

  public void performFly() {
    flyBehavior.fly();
  }

  public void performQuack() {
    quackBehavior.quack();
  }

  public void setFlyBehavior(FlyBehavior flyBehavior) {
    this.flyBehavior = flyBehavior;
  }

  public void setQuackBehavior(QuackBehavior quackBehavior) {
    this.quackBehavior = quackBehavior;
  }

  public void swim() {
    System.out.println("모든 오리는 물에 뜸니다. 가짜 오리도 뜨죠");
  }

}
