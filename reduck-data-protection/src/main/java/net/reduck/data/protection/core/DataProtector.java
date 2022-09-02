package net.reduck.data.protection.core;

/**
 * @author Reduck
 * @since 2022/8/26 11:48
 */
public interface DataProtector {

    <T> T handle(T value);

}
