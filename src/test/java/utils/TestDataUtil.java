package utils;

import java.util.Random;
public class TestDataUtil {
    private static Random rand = new Random();

    public static String generateRandomEmail() {
        int randomNumber = rand.nextInt(1000, 9999);
        return ConfigReader.getProperty("default.email.prefix") + randomNumber + ConfigReader.getProperty("default.email.domain");
    }

    public static String generateRandomPhoneNumber() {
        int randomNumber = rand.nextInt(1000, 9999);
        return ConfigReader.getProperty("phone.prefix") + randomNumber;
    }
}
