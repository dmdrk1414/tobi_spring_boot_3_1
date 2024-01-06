package springbook.user.learningtest.template;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        Resource resource = new ClassPathResource("numbers.txt");

        int sum = calculator.calcSum(resource.getURI().getPath());

        assertThat(sum).isEqualTo(10);
    }
}