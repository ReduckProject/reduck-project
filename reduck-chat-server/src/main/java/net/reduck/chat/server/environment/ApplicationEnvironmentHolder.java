package net.reduck.chat.server.environment;

/**
 * @author Reduck
 * @since 2023/1/9 16:45
 */
public class ApplicationEnvironmentHolder {

    private static ApplicationEnvironment environment;

    public static ApplicationEnvironment getEnvironment() {
        return environment;
    }

    public static void update(ApplicationEnvironment applicationEnvironment) {
        environment = applicationEnvironment;
    }
}
