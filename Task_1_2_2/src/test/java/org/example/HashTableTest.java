package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        hashTable.put("key3", 3);

        assertEquals(1, hashTable.get("key1"));
        assertEquals(2, hashTable.get("key2"));
        assertEquals(3, hashTable.get("key3"));
        assertNull(hashTable.get("nonExistingKey"));
    }

    @Test
    void testRemove() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        hashTable.put("key3", 3);

        assertEquals(3, hashTable.get("key3"));
        hashTable.remove("key3");
        assertNull(hashTable.get("key3"));

        assertEquals(2, hashTable.get("key2"));
        hashTable.remove("key2");
        assertNull(hashTable.get("key2"));

        assertEquals(1, hashTable.get("key1"));
        hashTable.remove("key1");
        assertNull(hashTable.get("key1"));
    }

    @Test
    void testUpdate() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);

        assertEquals(1, hashTable.get("key1"));
        assertEquals(2, hashTable.get("key2"));

        hashTable.update("key1", 10);
        assertEquals(10, hashTable.get("key1"));

        hashTable.update("key2", 20);
        assertEquals(20, hashTable.get("key2"));
    }

    @Test
    void testContainsKey() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);

        assertTrue(hashTable.containsKey("key1"));
        assertTrue(hashTable.containsKey("key2"));
        assertFalse(hashTable.containsKey("key3"));
    }

    @Test
    void testResize() {
        for (int i = 1; i <= 20; i++) {
            hashTable.put("key" + i, i);
        }

        for (int i = 1; i <= 20; i++) {
            assertEquals(i, hashTable.get("key" + i));
        }
    }

    @Test
    void testIterator() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        hashTable.put("key3", 3);

        var iterator = hashTable.iterator();
        assertTrue(iterator.hasNext());

        int itemCount = 0;
        while (iterator.hasNext()) {
            iterator.next();
            itemCount++;
        }
        assertEquals(3, itemCount);
    }

    @Test
    void testToString() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        hashTable.put("key3", 3);

        String tableString = hashTable.toString();
        assertTrue(tableString.contains("key1=1"));
        assertTrue(tableString.contains("key2=2"));
        assertTrue(tableString.contains("key3=3"));
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> otherTable = new HashTable<>();

        hashTable.put("key1", 1);
        hashTable.put("key2", 2);

        otherTable.put("key1", 1);
        otherTable.put("key2", 2);

        assertTrue(hashTable.equals(otherTable));

        otherTable.put("key3", 3);
        assertFalse(hashTable.equals(otherTable));
    }
}
