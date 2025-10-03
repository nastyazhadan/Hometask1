package ru.aston.hometask1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Tests {
    private static final Logger log = LogManager.getLogger(Tests.class);

    public static void main(String[] args) {
        MyHashMap<String, Integer> m1 = new MyHashMap<>();
        test(m1, "Default constructor");

        MyHashMap<String, Integer> m2 = new MyHashMap<>(10);
        test(m2, "Constructor with initialCapacity");

        MyHashMap<String, Integer> m3 = new MyHashMap<>(10, 0.5f);
        test(m3, "Constructor with initialCapacity & loadFactor");

        m3.put(null, 100);
        m3.put(null, 200);
        log.info("Null is presented: {}, value={}, size={}",
                m3.containsKey(null), m3.get(null), m3.size());
    }

    private static void test(MyHashMap<String, Integer> map, String title) {
        log.info("\n === {} === \n", title);

        log.info("Initial capacity: {}", map.getCapacity());

        for (int i = 1; i <= 10; i++) {
            map.put("K" + i, i * 10);
        }
        log.info("Size: {}", map.size());

        int oldValue = map.put("K1", 999);
        int newValue = map.get("K1");
        log.info("Old value for K1: {}, new: {}", oldValue, newValue);
        map.put("K2", 888);

        log.info("Contains key K1: {}", map.containsKey("K1"));
        log.info("Contains key K15: {}", map.containsKey("K15"));
        log.info("Contains value 888: {}", map.containsValue(888));
        log.info("Contains value 777: {}", map.containsValue(777));

        map.remove("K4");
        log.info("After remove K4, contains key K4: {}", map.containsKey("K4"));
        log.info("Size: {}", map.size());
        log.info("If key (K1000) doesn't exist, after remove result is {}", map.remove("K1000"));


        log.info("Keys: {}", map.keySet());
        log.info("Values: {}", map.values());
        log.info("Entry set: {}", map.entrySet());

        map.clear();
        log.info("After clear, is Empty: {}, size={}", map.isEmpty(), map.size());
    }
}
