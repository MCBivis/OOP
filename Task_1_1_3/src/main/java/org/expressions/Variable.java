package org.expressions;

import org.Printable.IPrintable;

/**
 * Класс Variable представляет переменную в математическом выражении.
 */
public class Variable extends Expression {

    /** Имя переменной. */
    public final String name;

    /**
     * Конструктор для создания объекта Variable.
     *
     * @param name      имя переменной
     * @param printable объект, реализующий интерфейс IPrintable, для вывода
     */
    public Variable(String name, IPrintable printable) {
        super(printable);
        this.name = name;
    }

    /**
     * Выводит имя переменной.
     */
    @Override
    public void print() throws Exception{
        printable.print(name);
    }

    /**
     * Вычисляет производную переменной по заданной переменной.
     *
     * @param variable переменная, по которой нужно взять производную
     * @return 1, если переменная равна заданной, в противном случае 0
     */
    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1, printable);
        } else {
            return new Number(0, printable);
        }
    }

    /**
     * Вычисляет значение переменной для заданных переменных.
     *
     * @param vars строка, содержащая значения переменных
     * @return значение переменной, или -1, если переменная не найдена
     */
    @Override
    public int eval(String vars) {
        String[] assignments = vars.split(";");
        for (String assignment : assignments) {
            String[] pair = assignment.split("=");
            if (pair[0].trim().equals(name)) {
                return Integer.parseInt(pair[1].trim());
            }
        }
        return -1; // Переменная не найдена
    }

    /**
     * Возвращает текущую переменную без изменений.
     *
     * @return данная переменная
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
