package springbook.user.headfirst.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MallardDuckTest {

    @Test
    void display() {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();
    }
}