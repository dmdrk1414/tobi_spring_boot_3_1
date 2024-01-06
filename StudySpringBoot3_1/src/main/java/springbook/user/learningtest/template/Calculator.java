package springbook.user.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer sum = 0;
            String line = null;

            while ((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }

            return sum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                // BufferedReader 오브젝트가 생성되기 전에 예외가 발생할 수도있으므로
                // 드시 null 체크를 먼저해야 한다.
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}