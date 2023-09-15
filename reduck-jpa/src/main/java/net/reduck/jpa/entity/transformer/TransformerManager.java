package net.reduck.jpa.entity.transformer;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Gin
 * @since 2023/9/7 11:33
 */
public class TransformerManager {

    private static Set<ColumnTransformer> cache = new ConcurrentSkipListSet<>();

//    public static <T extends AttributeTransformer<?, ?>> T getInstance() {
//        return cache.
//    }
}
