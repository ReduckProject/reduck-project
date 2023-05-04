package net.reduck.chat.server.mock;

import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Reduck
 * @since 2023/1/13 14:49
 */
@Data
public class MockWorld {
    private final AtomicInteger age = new AtomicInteger(15);
    private final int maxChildCount;

//    private

    private AtomicReference<MocPerson> single = new AtomicReference<>();

    private List<MocPerson> singleMan = new ArrayList<>();
    private List<MocPerson> singleWomen = new ArrayList<>();
    private List<MocPerson> mocPeople = new ArrayList<>(10000000);

    public MockWorld(int wordAge, int couple, int maxChildCount) {
        this.maxChildCount = maxChildCount;
        this.age.set(wordAge);

        init();

        for (int i = 0; i < couple; i++) {
            initCouple(26, 26);
        }
    }

    public void run(int round) {
        for (int r = 0; r < round; r++) {
            pass();
        }
    }

    private void initCouple(int manAge, int womenAge) {
        MocPerson man = new MocPerson();
        MocPerson women = new MocPerson();
        man.setAge(manAge);
        man.setPartner(women);

        women.setPartner(man);
        women.setAge(womenAge);

        mocPeople.add(man);
        mocPeople.add(women);
    }

    private void pass() {
        newPeople.clear();
        marryCount.set(0);

        StringBuilder builder = new StringBuilder();
        builder.append("Init people ").append(mocPeople.size()).append(",");

        // 结婚
        for (MocPerson person : mocPeople) {
            marry(person);
        }

        newPeople.clear();
        // 生孩子
        for (MocPerson person : mocPeople) {
            born(person);
        }

        // 死亡
        for (MocPerson person : mocPeople) {
            person.setAge(person.getAge());
        }

        // 人口更新
        List<MocPerson> life = new ArrayList<>();
        for (MocPerson person : mocPeople) {
            if (!dead(person)) {
                life.add(person);
            }
        }

        builder.append("marry : ").append(marryCount.get()).append(",");
        builder.append("dead :").append(mocPeople.size() - life.size()).append(",");
        builder.append("new :").append(newPeople.size()).append(",");

        mocPeople.clear();
        mocPeople.addAll(life);
        mocPeople.addAll(newPeople);
        newPeople.clear();
        age.addAndGet(10);

        builder.append("total : ").append(mocPeople.size());

        // 年龄增大
        for (MocPerson person : mocPeople) {
            person.setAge(person.getAge() + 10);
        }


        System.out.println(builder.toString());
    }

    AtomicInteger marryCount = new AtomicInteger(0);

    private void marry(MocPerson person) {

//        System.out.println(person.getAge());
        if (person.getPartner() == null && (person.getAge() / 10 == 2 || person.getAge() / 10 == 3 || person.getAge() / 10 == 4)) {
//            System.out.println(person.getAge());
            if (person.isMan()) {
                if (singleWomen.size() > 0) {
                    MocPerson partner = singleWomen.get(0);
                    person.setPartner(partner);
                    singleWomen.remove(0);
                    marryCount.incrementAndGet();

                } else {
                    singleMan.add(person);
                }
            } else {
                if (singleMan.size() > 0) {
                    MocPerson partner = singleMan.get(0);
                    person.setPartner(partner);
                    singleMan.remove(0);
                    marryCount.incrementAndGet();

                } else {
                    singleWomen.add(person);
                }
            }
        }
    }

    private List<MocPerson> newPeople = new ArrayList<>();

    private void born(MocPerson person) {
        if (person.getPartner() == null) {
            return;
        }

        MocPerson partner = person.getPartner();

        if (!person.isNext()) {
            List<MocPerson> children = new ArrayList<>();
//            int count = random.nextInt(maxChildCount) + 1;
            int count = maxChildCount;
            for (int i = 0; i < count; i++) {
                MocPerson child = new MocPerson();
                child.setMan(random.nextInt(2) == 0);
                child.setDad(person.isMan() ? person : partner);
                child.setMom(person.isMan() ? partner : person);
                children.add(child);
            }

            partner.getChildren().addAll(children);
            person.getChildren().addAll(children);

            newPeople.addAll(children);
        }

        person.setNext(true);
        partner.setNext(true);
    }

    Map<Integer, Integer> mortalityMap = new HashMap<>();

    public void init() {
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

    boolean dead(int age) {
        return mortalityMap.getOrDefault(age % 10, 100) > random.nextInt(100);
    }

    private boolean dead(MocPerson person) {
        boolean dead = mortalityMap.getOrDefault(person.getAge() % 10, 100) > random.nextInt(100);
        if (dead) {
            if (person.getPartner() != null) {
                person.getPartner().setPartner(null);
                for (MocPerson child : person.getChildren()) {
                    if (person.isMan()) {
                        child.setDad(null);
                        child.setMom(null);
                    }
                }
            }
        }

        return dead;
    }

    private
    Random random = new Random();


    public static void main(String[] args) {
        MockWorld world = new MockWorld(15, 64, 2);
        world.run(200);
    }

}
