package net.reduck.chat.server.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Reduck
 * @since 2023/1/13 14:37
 */
public class Mock {
    Map<Integer, Integer> mortalityMap = new HashMap<>();

    public Mock() {
        mortalityMap.put(0, 1);
        mortalityMap.put(1, 10);
        mortalityMap.put(2, 15);
        mortalityMap.put(3, 15);
        mortalityMap.put(4, 20);
        mortalityMap.put(5, 20);
        mortalityMap.put(6, 20);
        mortalityMap.put(7, 40);
        mortalityMap.put(8, 60);
        mortalityMap.put(9, 90);
        mortalityMap.put(10, 99);
    }

    public void pass(int years) {

    }

    boolean dead(int age) {
        return mortalityMap.getOrDefault(age % 10, 100) > random.nextInt(100);
    }

    private Random random = new Random();
}
