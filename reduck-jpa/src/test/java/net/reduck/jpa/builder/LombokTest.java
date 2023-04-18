package net.reduck.jpa.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Gin
 * @since 2023/4/4 10:49
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
//@NoArgsConstructor
public class LombokTest {

    private String name;

    private String name1;

    private String name2;
}
