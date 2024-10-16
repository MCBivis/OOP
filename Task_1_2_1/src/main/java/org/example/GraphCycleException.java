package org.example;

/**
 * Исключение, выбрасываемое в случае обнаружения цикла в графе при выполнении
 * операций, таких как топологическая сортировка.
 */
public class GraphCycleException extends Exception {

    /**
     * Конструктор класса GraphCycleException.
     *
     * @param message Сообщение об ошибке, передаваемое при возникновении исключения.
     */
    public GraphCycleException(String message) {
        super(message);
    }
}
