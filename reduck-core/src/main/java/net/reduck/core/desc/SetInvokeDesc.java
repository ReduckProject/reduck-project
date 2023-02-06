package net.reduck.core.desc;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gin
 * @since 2023/2/6 15:44
 */
@AllArgsConstructor
@Getter
public class SetInvokeDesc {

    private InstanceDesc getterInstance;

    private InstanceDesc setterInstance;

    private TypeDesc parameter;

}
