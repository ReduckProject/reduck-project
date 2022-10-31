package org.reduck.shepherd;

import java.util.Objects;

/**
 * @author Gin
 * @since 2022/10/31 16:36
 */
public abstract class AbstractCache {
    private String name;
    private String group;

    public AbstractCache(String groupName, String name) {
        CacheManager.register(this);
    }

    /**
     * 清除缓存数据
     */
    public abstract void clean();

    /**
     * 初始化缓存
     */
    public abstract void init();

    /**
     * 刷新缓存
     */
    public void refresh(){
        clean();
        init();
    }

    String getGroup() {
        return this.group;
    }

    String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCache)) return false;
        AbstractCache cache = (AbstractCache) o;
        return Objects.equals(name, cache.name) &&
                Objects.equals(group, cache.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, group);
    }
}
