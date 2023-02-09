package net.reduck.api.doc;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * @author Reduck
 * @since 2022/12/13 16:06
 */
public class DataTest {

    @Test
    public void testGenerateFullname() {
        Faker faker = new Faker(Locale.CHINESE);

        System.out.printf(faker.name().fullName());
    }
}
