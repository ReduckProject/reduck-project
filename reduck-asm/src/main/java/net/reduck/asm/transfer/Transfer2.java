package net.reduck.asm.transfer;

/**
 * @author Gin
 * @since 2023/2/3 14:58
 */
public class Transfer2 {

    public static AppleTO tranfer(Apple apple) {
        AppleTO appleTO = new AppleTO();
        appleTO.setName(apple.getName());
        appleTO.setHeight(apple.getHeight());
        appleTO.setWidth(apple.getWidth());
        appleTO.setPassword(apple.getPassword());
        return appleTO;
    }
}
