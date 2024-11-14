package org.example;

/**
 * Класс для хранения пар ключ-значение.
 */
public class Entry<K, V> {
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