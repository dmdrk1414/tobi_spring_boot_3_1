package springbook.user.headfirst.strategy;

import org.junit.jupiter.api.Test;
import springbook.user.headfirst.strategy.fly.FlyRocketPowered;

import static org.junit.jupiter.api.Assertions.*;

class MallardDuckTest {

    @Test
    void display() {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();
    }

    @Test
    void display_set_test() {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();

        System.out.println();
        mallard.setFlyBehavior(new FlyRocketPowered());
        mallard.performFly();
    }
}