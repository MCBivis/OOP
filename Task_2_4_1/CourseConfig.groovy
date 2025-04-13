course.tasks {
    task('Task_2_4_1') {
        title = 'Автоматическая проверка'
        maxScore = 1
        softDeadline = '2025-04-15'
        hardDeadline = '2025-04-22'
    }
}

course.groups {
    group('23216') {
        student {
            github = 'MCBivis'
            fullName = 'Кулешов Артемий'
            repo = 'https://github.com/MCBivis/OOP'
        }
        student {
            github = 'nkrainov'
            fullName = 'Крайнов Никита'
            repo = 'https://github.com/nkrainov/OOP'
        }
    }
}
