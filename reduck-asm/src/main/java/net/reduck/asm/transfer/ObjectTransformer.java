package net.reduck.asm.transfer;

/**
 * @author Gin
 * @since 2023/2/3 16:18
 */
public interface ObjectTransformer<T, R> {

    public R transfer(T original);

}
