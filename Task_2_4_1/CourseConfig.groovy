course.tasks {
    task('Task_1_1_3') {
        title = 'Операции с уравнениями'
        maxScore = 1
        softDeadline = '2024-10-01'
        hardDeadline = '2024-10-08'
    }
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

course.assignments {
    assign ('Task_2_4_1', 'MCBivis')
    assign ('Task_2_4_1', 'nkrainov')
    assign ('Task_1_1_3', 'MCBivis')
    assign ('Task_1_1_3', 'nkrainov')
}

course.milestones {
    milestone ('3 семестр', '2024-12-31')
    milestone ('4 семестр', '2025-05-31')
}

course.settings {
    bonusPoints = ['MCBivis': ['Task_2_4_1': 0.5]]
    testTimeoutStrategy = 'skipAfter60Seconds'
}