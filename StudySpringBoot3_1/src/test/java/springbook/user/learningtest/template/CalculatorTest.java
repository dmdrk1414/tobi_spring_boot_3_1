package springbook.user.learningtest.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    Calculator calculator;
    String numFilepath;

    @BeforeEach
    public void setUp() {
        try {
            this.calculator = new Calculator();
            Resource resource = new ClassPathResource("numbers.txt");
            this.numFilepath = resource.getURI().getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sumOfNumbers() throws IOException {
        int sum = calculator.calcSum(numFilepath);

        assertThat(sum).isEqualTo(10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(this.numFilepath)).isEqualTo(24);
    }
}