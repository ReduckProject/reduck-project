package net.reduck.concurrent;

/**
 * @author Reduck
 * @since 2022/9/13 15:47
 */
public enum SingletonTask {

    INSTANCE;

    SingletonTask() {

    }

    private String name;

    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
