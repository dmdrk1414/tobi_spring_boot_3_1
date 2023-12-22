package springbook.user.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarTest {
    @Autowired
    private Control control;


    @Test
    public void testSound() {

        control.soundControl();
    }
}