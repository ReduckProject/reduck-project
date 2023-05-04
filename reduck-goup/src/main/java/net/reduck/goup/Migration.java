package net.reduck.goup;

/**
 * @author Reduck
 * @since 2022/12/13 10:59
 */
public interface Migration {

    boolean prepare();

    boolean execute();

    boolean complete();

}
