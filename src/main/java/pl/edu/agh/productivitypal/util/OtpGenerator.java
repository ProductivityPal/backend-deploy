package pl.edu.agh.productivitypal.util;

import java.util.Random;
import java.util.stream.IntStream;

public class OtpGenerator {
    private static final Random random = new Random();

    public static String generateCode() {
        StringBuilder builder = new StringBuilder();
        IntStream.range(0, 6).forEach(v -> builder.append(randomDigit()));
        return builder.toString();
    }

    private static int randomDigit() {
        return random.nextInt(10);
    }
}
