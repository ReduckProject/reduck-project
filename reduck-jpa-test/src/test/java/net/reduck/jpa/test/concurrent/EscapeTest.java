package net.reduck.jpa.test.concurrent;

import org.junit.Test;
import org.springframework.data.jpa.repository.query.EscapeCharacter;

/**
 * @author Reduck
 * @since 2022/9/16 10:22
 */
public class EscapeTest {
    private String specialCharacter = "%<_>/\\";

    EscapeCharacter escape = EscapeCharacter.DEFAULT;
    @Test
    public void testEscape() {
        StringBuilder sb = new StringBuilder();

        System.out.println(EscapeCharacter.DEFAULT.escape(specialCharacter)
        );

        System.out.println(String.format("%s%%", escape.escape(specialCharacter)));
    }
}
