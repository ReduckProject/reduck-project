package net.reduck.core.desc;

import lombok.Getter;

/**
 * @author Gin
 * @since 2023/2/6 15:41
 */
@Getter
public class SetterParameterDesc extends TypeDesc {
    private final InstanceDesc instance;

    public SetterParameterDesc(InstanceDesc instance) {
        super(instance.getType().getName());
        this.instance = instance;
    }

    public String getGetterDesc() {
        return "()" + getName() + ";";
    }

    public String getSetterDesc() {
        return "(L" + getName() + ";)V";
    }
}
