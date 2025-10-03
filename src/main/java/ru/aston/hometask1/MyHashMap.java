package ru.aston.hometask1;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class MyHashMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private static final class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V old = value;
            value = newValue;
            return old;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private Node<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        int capacity = DEFAULT_INITIAL_CAPACITY;
        this.table = new Node[capacity];
        this.threshold = (int) (capacity * loadFactor);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity) {
        if (initialCapacity < 1) {
            initialCapacity = DEFAULT_INITIAL_CAPACITY;
        }
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.table = new Node[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 1) {
            initialCapacity = DEFAULT_INITIAL_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            loadFactor = DEFAULT_LOAD_FACTOR;
        }
        this.loadFactor = loadFactor;
        this.table = new Node[initialCapacity];
        this.threshold = Math.max(1, (int) (initialCapacity * loadFactor));
    }

    public int getCapacity() {
        return table.length;
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int idx = indexFor(hash, table.length);

        for (Node<K, V> e = table[idx]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        table[idx] = new Node<>(hash, key, value, table[idx]);
        size++;
        if (size > threshold) {
            resize();
        }
        return null;
    }

    public V get(Object key) {
        int hash = hash(key);
        int idx = indexFor(hash, table.length);
        for (Node<K, V> e = table[idx]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                return e.value;
            }
        }
        return null;
    }

    public V remove(Object key) {
        int hash = hash(key);
        int idx = indexFor(hash, table.length);

        Node<K, V> prev = null;
        Node<K, V> curr = table[idx];
        while (curr != null) {
            if (curr.hash == hash && Objects.equals(curr.key, key)) {
                if (prev == null) table[idx] = curr.next;
                else prev.next = curr.next;
                size--;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public boolean containsKey(Object key) {
        int hash = hash(key);
        int idx = indexFor(hash, table.length);
        for (Node<K, V> e = table[idx]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (Node<K, V> head : table) {
            for (Node<K, V> e = head; e != null; e = e.next) {
                if (Objects.equals(e.value, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Node<K, V> head : table) {
            for (Node<K, V> e = head; e != null; e = e.next) {
                keys.add(e.key);
            }
        }
        return keys;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new HashSet<>();
        for (Node<K, V> head : table) {
            for (Node<K, V> e = head; e != null; e = e.next) {
                entries.add(e);
            }
        }
        return entries;
    }

    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Node<K, V> head : table) {
            for (Node<K, V> e = head; e != null; e = e.next) {
                values.add(e.value);
            }
        }
        return values;
    }

    public int size() {
        return size;
    }

    private static int indexFor(int hash, int length) {
        int idx = hash % length;
        return (idx < 0) ? idx + length : idx;
    }

    private static int hash(Object key) {
        return (key == null) ? 0 : key.hashCode();
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTab = table;
        int newCap = Math.max(1, oldTab.length << 1);
        Node<K, V>[] newTab = new Node[newCap];

        for (Node<K, V> head : oldTab) {
            Node<K, V> e = head;
            while (e != null) {
                Node<K, V> next = e.next;
                int i = indexFor(e.hash, newCap);
                e.next = newTab[i];
                newTab[i] = e;
                e = next;
            }
        }
        table = newTab;
        threshold = (int) (newCap * loadFactor);
    }
}
