package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс GradeBook представляет зачётную книжку студента, позволяя хранить,
 * добавлять оценки и вычислять различные параметры, такие как средний балл,
 * возможность перевода на бюджет, получение "красного диплома" и повышенной стипендии.
 */
public class GradeBook {
    private final List<GradeEntry> grades; // Список оценок студента
    private final boolean isPaidForm; // Индикатор платной формы обучения (true - платное, false - бюджетное)

    /**
     * Конструктор для создания экземпляра зачётной книжки.
     *
     * @param isPaidForm true, если студент учится на платной основе, false для бюджетной основы.
     */
    public GradeBook(boolean isPaidForm) {
        this.grades = new ArrayList<>();
        this.isPaidForm = isPaidForm;
    }

    /**
     * Добавляет новую оценку в зачётную книжку.
     *
     * @param subject  название предмета.
     * @param type     тип контроля (например, "Экзамен" или "Зачет").
     * @param semester номер семестра (от 1 до 8).
     * @param grade    оценка (от 2 до 5).
     * @throws IllegalArgumentException если номер семестра или оценка некорректны.
     */
    public void addGrade(String subject, String type, int semester, int grade) {
        if (semester < 1 || semester > 8) {
            throw new IllegalArgumentException("Некорректный семестр: " + semester + ". Семестр должен быть в диапазоне от 1 до 8.");
        }
        if (grade < 2 || grade > 5) {
            throw new IllegalArgumentException("Некорректная оценка: " + grade + ". Оценка должна быть в диапазоне от 2 до 5.");
        }
        grades.add(new GradeEntry(subject, type, semester, grade));
    }

    /**
     * Вычисляет средний балл студента.
     *
     * @return средний балл, или 0, если оценок нет.
     */
    public double calculateAverageGrade() {
        double totalGrade = 0;
        for (GradeEntry grade : grades) {
            totalGrade += grade.getGrade();
        }
        return grades.isEmpty() ? 0 : totalGrade / grades.size();
    }

    /**
     * Проверяет, может ли студент перевестись на бюджетную основу обучения.
     *
     * @return true, если студент соответствует критериям перевода, иначе false.
     */
    public boolean canTransferToBudget() {
        int currentSemester = getCurrentSemester();
        for (GradeEntry grade : grades) {
            if (grade.getGrade() < 4 && grade.getSemester() >= currentSemester - 1 &&
                    !(grade.getGrade() == 3 && (grade.getType().equals("Экзамен") || grade.getType().equals("Дифференцированный зачет")))) {
                return false;
            }
        }
        return isPaidForm; // Только для студентов на платной основе
    }

    /**
     * Проверяет, может ли студент получить "красный диплом".
     *
     * @return true, если студент соответствует критериям, иначе false.
     */
    public boolean canGetRedDiploma() {
        for (GradeEntry grade : grades) {
            if (grade.getGrade() < 4 || (grade.getType().equals("Защита ВКР") && grade.getGrade() < 5)) {
                return false;
            }
        }
        return !(getCurrentSemester() == 8 && calculateAverageGrade() < 5 * 0.75);
    }

    /**
     * Проверяет, может ли студент получать повышенную стипендию.
     *
     * @return true, если студент соответствует критериям, иначе false.
     */
    public boolean canGetIncreasedScholarship() {
        int currentSemester = getCurrentSemester();
        for (GradeEntry grade : grades) {
            if (grade.getSemester() == currentSemester && grade.getGrade() < 4) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает текстовое представление зачётной книжки.
     *
     * @return строка, представляющая зачётную книжку.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Зачетная книжка:\n");
        sb.append(String.format("%-11s %-15s %-10s %-10s%n", "Предмет", "Тип контроля", "Семестр", "Оценка"));
        sb.append("----------------------------------------------------\n");
        for (GradeEntry grade : grades) {
            sb.append(String.format("%-11s %-15s %-10d %-10d%n",
                    grade.getSubject(), grade.getType(), grade.getSemester(), grade.getGrade()));
        }
        sb.append("----------------------------------------------------\n");
        sb.append(String.format("Средний балл: %.2f%n", calculateAverageGrade()));
        sb.append("Перевод на бюджет: ").append(isPaidForm ? (canTransferToBudget() ? "Да" : "Нет") : "Уже на бюджете").append("\n");
        sb.append("Красный диплом: ").append(canGetRedDiploma() ? "Да" : "Нет").append("\n");
        sb.append("Повышенная стипендия: ").append(canGetIncreasedScholarship() ? "Да" : "Нет").append("\n");
        return sb.toString();
    }

    /**
     * Определяет текущий семестр на основе данных о семестрах оценок.
     *
     * @return текущий семестр.
     */
    private int getCurrentSemester() {
        int currentSemester = 0;
        for (GradeEntry grade : grades) {
            if (grade.getSemester() > currentSemester) {
                currentSemester = grade.getSemester();
            }
        }
        return currentSemester;
    }

    /**
     * Вспомогательный класс для хранения данных об оценке.
     */
    private static class GradeEntry {
        private final String subject;
        private final String type;
        private final int semester;
        private final int grade;

        /**
         * Конструктор для создания записи об оценке.
         *
         * @param subject  название предмета.
         * @param type     тип контроля.
         * @param semester семестр.
         * @param grade    оценка.
         */
        public GradeEntry(String subject, String type, int semester, int grade) {
            this.subject = subject;
            this.type = type;
            this.semester = semester;
            this.grade = grade;
        }

        /**
         * Возвращает предмет.
         *
         * @return предмет.
         */
        public String getSubject() {
            return subject;
        }

        /**
         * Возвращает тип экзамена.
         *
         * @return тип экзамена.
         */
        public String getType() {
            return type;
        }

        /**
         * Возвращает семестр.
         *
         * @return семестр.
         */
        public int getSemester() {
            return semester;
        }

        /**
         * Возвращает оценку.
         *
         * @return оценку.
         */
        public int getGrade() {
            return grade;
        }
    }
}
