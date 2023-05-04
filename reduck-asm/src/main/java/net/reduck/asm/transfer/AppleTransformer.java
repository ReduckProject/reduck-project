package net.reduck.asm.transfer;

/**
 * @author Gin
 * @since 2023/2/3 16:21
 */
public class AppleTransformer implements ObjectTransformer<Apple, AppleTO> {

    @Override
    public AppleTO transfer(Apple original) {
        AppleTO to = new AppleTO();

        System.out.println();
        return to;
    }
}
