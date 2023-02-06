package net.reduck.core.desc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gin
 * @since 2023/2/6 15:40
 */
@AllArgsConstructor
@Getter
public class MethodDesc {

    private String name;

    private SetterParameterDesc[] parameters;

    private SetterParameterDesc returnType;

}
