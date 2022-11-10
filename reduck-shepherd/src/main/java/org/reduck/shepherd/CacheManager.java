package org.reduck.shepherd;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Reduck
 * @since 2022/10/31 17:29
 */
public class CacheManager {
    private static final Set<AbstractCache> CACHE_MANAGER = new ConcurrentSkipListSet<>();

    public static void clean(String name) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getName() != null && cache.getName().equals(name)) {
                cache.clean();
            }
        }
    }

    public static void cleanGroup(String name) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getGroup() != null && cache.getGroup().equals(name)) {
                cache.clean();
            }
        }
    }

    public static void clean(Class target) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getClass().isAssignableFrom(target)) {
                cache.clean();
            }
        }
    }

    public static void cleanAll() {
        for (AbstractCache cache : CACHE_MANAGER) {
            cache.clean();
        }
    }

    public static void init(String name) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getName() != null && cache.getName().equals(name)) {
                cache.init();
            }
        }
    }

    public static void initGroup(String name) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getGroup() != null && cache.getGroup().equals(name)) {
                cache.init();
            }
        }
    }

    public static void init(Class target) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getClass().isAssignableFrom(target)) {
                cache.init();
            }
        }
    }

    public static void initAll() {
        for (AbstractCache cache : CACHE_MANAGER) {
            cache.init();
        }
    }


    public static void refresh(String name) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getName() != null && cache.getName().equals(name)) {
                cache.refresh();
            }
        }
    }

    public static void refreshGroup(String name) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getGroup() != null && cache.getGroup().equals(name)) {
                cache.refresh();
            }
        }
    }

    public static void refresh(Class target) {
        for (AbstractCache cache : CACHE_MANAGER) {
            if (cache.getClass().isAssignableFrom(target)) {
                cache.refresh();
            }
        }
    }

    public static void refreshAll() {
        for (AbstractCache cache : CACHE_MANAGER) {
            cache.refresh();
        }
    }


    static void register(AbstractCache cache) {
        CACHE_MANAGER.add(cache);
    }
}
