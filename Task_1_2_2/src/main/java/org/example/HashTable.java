package org.example;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Реализация параметризованного контейнера HashTable<K, V>.
 *
 * @param <K> Тип ключа
 * @param <V> Тип значения
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private int modificationCount;

    /**
     * Внутренний класс для хранения пар ключ-значение.
     */
    public static class Entry<K, V> {
        private final K key;
        private V value;

        /**
         * Конструктор для создания новой пары ключ-значение.
         *
         * @param key   Ключ
         * @param value Значение
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Возвращает ключ.
         *
         * @return Ключ
         */
        public K getKey() {
            return key;
        }

        /**
         * Возвращает значение.
         *
         * @return Значение
         */
        public V getValue() {
            return value;
        }

        /**
         * Устанавливает новое значение для ключа.
         *
         * @param value Новое значение
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Выводит пару ключ=значение в строку.
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Создает пустую хеш-таблицу.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        table = new LinkedList[DEFAULT_CAPACITY];
        size = 0;
        modificationCount = 0;
    }

    /**
     * Добавляет пару ключ-значение в хеш-таблицу.
     *
     * @param key   Ключ
     * @param value Значение
     */
    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
        modificationCount++;
        if (size > table.length * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Удаляет пару ключ-значение по ключу.
     *
     * @param key Ключ для удаления
     */
    public void remove(K key) {
        int index = hash(key);
        if (table[index] != null) {
            for (int i = 0; i < table[index].size(); i++) {
                Entry<K, V> entry = table[index].get(i);
                if (entry.getKey().equals(key)) {
                    table[index].remove(i);
                    size--;
                    modificationCount++;
                    break;
                }
            }
        }
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key Ключ
     * @return Значение, соответствующее ключу, или null, если ключ не найден
     */
    public V get(K key) {
        int index = hash(key);
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Обновляет значение по ключу.
     *
     * @param key   Ключ
     * @param value Новое значение
     */
    public void update(K key, V value) {
        put(key, value);
    }

    /**
     * Проверяет наличие значения по ключу.
     *
     * @param key Ключ
     * @return true, если ключ присутствует в хеш-таблице
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Возвращает итератор для обхода элементов хеш-таблицы.
     *
     * @return Итератор
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Сравнивает хеш-таблицу с другой хеш-таблицей.
     *
     * @param other Другая хеш-таблица
     * @return true, если таблицы равны
     */
    public boolean equals(HashTable<K, V> other) {
        if (this.size != other.size) return false;
        for (Entry<K, V> entry : this) {
            V otherValue = other.get(entry.getKey());
            if(otherValue == null) {
                return false;
            }
            if (!entry.getValue().equals(otherValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Преобразует хеш-таблицу в строковое представление.
     *
     * @return Строковое представление хеш-таблицы
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (LinkedList<Entry<K, V>> list : table) {
            if (list != null) {
                for (Entry<K, V> entry : list) {
                    sb.append(entry.toString()).append(", ");
                }
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Вычисляет хеш-код для ключа.
     *
     * @param key Ключ
     * @return Индекс в таблице
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Увеличивает размер хеш-таблицы при необходимости, когда количество элементов превышает пороговое значение.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<Entry<K, V>>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        size = 0;
        for (LinkedList<Entry<K, V>> list : oldTable) {
            if (list != null) {
                for (Entry<K, V> entry : list) {
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * Реализация итератора для хеш-таблицы.
     */
    private class HashTableIterator implements Iterator<Entry<K, V>> {
        private int currentListIndex;
        private Iterator<Entry<K, V>> currentListIterator;
        private final int expectedModCount;

        /**
         * Конструктор итератора для хеш-таблицы.
         */
        public HashTableIterator() {
            currentListIndex = -1;
            expectedModCount = modificationCount;
            advanceToNextList();
        }

        /**
         * Перемещается к следующему непустому списку в таблице.
         */
        private void advanceToNextList() {
            currentListIterator = null;
            while (currentListIndex + 1 < table.length) {
                currentListIndex++;
                if (table[currentListIndex] != null && !table[currentListIndex].isEmpty()) {
                    currentListIterator = table[currentListIndex].iterator();
                    break;
                }
            }
        }

        /**
         * Проверяет, есть ли следующий элемент для итерации.
         *
         * @return true, если есть следующий элемент
         */
        @Override
        public boolean hasNext() {
            if (modificationCount != expectedModCount) {
                throw new ConcurrentModificationException("Хеш-таблица была изменена!");
            }

            if (currentListIterator == null) {
                return false;
            }

            if (currentListIterator.hasNext()) {
                return true;
            } else {
                advanceToNextList();
                return currentListIterator != null;
            }
        }

        /**
         * Возвращает следующий элемент в хеш-таблице.
         *
         * @return Следующий элемент
         */
        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new IllegalStateException("Нет больше элементов для итерации");
            }

            return currentListIterator.next();
        }
    }
}
