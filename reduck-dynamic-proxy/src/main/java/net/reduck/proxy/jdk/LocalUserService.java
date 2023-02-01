package net.reduck.proxy.jdk;

/**
 * @author Gin
 * @since 2023/2/1 10:51
 */
public class LocalUserService implements UseService {

    @Override
    public String getName() {
        return "Local user";
    }

    @Override
    public int getAge() {
        return 35;
    }
}
