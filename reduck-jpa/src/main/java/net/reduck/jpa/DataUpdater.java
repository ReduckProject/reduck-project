package net.reduck.jpa;

import lombok.Data;

import java.util.*;
import java.util.function.Function;

/**
 * @author Gin
 * @since 2023/5/19 10:53
 */
public class DataUpdater<K, V> {

    private Map<K, V> newData = new HashMap<>();
    private Map<K, V> oldData = new HashMap<>();

    private List<V> add = new ArrayList<>();
    private List<V> delete = new ArrayList<>();

    private List<V> update = new ArrayList<>();

    Function<V, K> keyFunction;

    Comparator<V> comparator;

    public DataUpdater(List<V> oldData, List<V> newData, Function<V, K> keyFunction, Comparator<V> comparator) {
        if (oldData != null) {
            oldData.forEach(data -> this.oldData.put(keyFunction.apply(data), data));
        }

        if (newData != null) {
            newData.forEach(data -> this.newData.put(keyFunction.apply(data), data));
        }

        this.keyFunction = keyFunction;
        this.comparator = comparator;
        init();
    }

    public DataUpdater(List<V> oldData, List<V> newData, Function<V, K> keyFunction) {
        this(oldData, newData, keyFunction, (o1, o2) -> {
            if (o1 == null || o2 == null) {
                return o1 == o2 ? 0 : -1;
            }

            return o1.equals(o2) ? 0 : -1;
        });
    }

    public List<V> getAddList() {
        return add;
    }

    public List<V> getDeleteList() {
        return delete;
    }

    public List<V> getUpdateList() {
        return update;
    }

    private void init() {
        for (K key : newData.keySet()) {
            if (!oldData.containsKey(key)) {
                add.add(newData.get(key));
            } else {
                if (comparator.compare(oldData.get(key), newData.get(key)) != 0) {
                    update.add(newData.get(key));
                }
            }
        }

        for (V data : oldData.values()) {
            if (!newData.containsKey(keyFunction.apply(data))) {
                delete.add(data);
            }
        }
    }

    @Data
    public static class Test {

        public Test(String name1, String name2) {
            this.name1 = name1;
            this.name2 = name2;
        }

        private String name;

        private String  name1;

        private String name2;
    }

    public static void main(String[] args) {
        Test a1 = new Test("1", "2");
        Test a2 = new Test("3", "4");
        Test a3 = new Test("5", "6");

        Test b1 = new Test("1", "2");
        Test b2 = new Test("3", "5");
        DataUpdater<String, Test> updater = new DataUpdater<>(Arrays.asList(a1,a2, a3)
                , Arrays.asList(b1, b2)
                , Test::getName1
        );

        System.out.println(updater.getAddList());
        System.out.println(updater.getDeleteList());
        System.out.println(updater.getUpdateList());
    }
}
