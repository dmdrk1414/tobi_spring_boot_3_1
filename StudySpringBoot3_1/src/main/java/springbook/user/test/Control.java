package springbook.user.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class Control {
    private final Car car;

    @Autowired
    public Control(Car car) {
        this.car = car;
    }

    public void soundControl() {
        this.car.sound();
    }

    public void showColor() {
        System.out.println(this.car.color());
    }

    public void showPowerType() {
        this.car.powerType();
    }
}
