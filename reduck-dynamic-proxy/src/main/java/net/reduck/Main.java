package net.reduck;

/**
 * @author Reduck
 * @since ${DATE} ${TIME}
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        System.getProperties().forEach((k,v) -> System.out.println(k + ":" + v));
    }
}