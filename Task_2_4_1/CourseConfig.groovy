course.tasks {
    task('Task_1_1_2') {
        title = 'Консольный блэкджек'
        maxScore = 1
        softDeadline = '2024-09-17'
        hardDeadline = '2024-09-24'
    }
    task('Task_1_1_3') {
        title = 'Операции с уравнениями'
        maxScore = 1
        softDeadline = '2024-10-01'
        hardDeadline = '2024-10-08'
    }
    task('Task_2_4_1') {
        title = 'Автоматическая проверка'
        maxScore = 1
        softDeadline = '2025-04-18'
        hardDeadline = '2025-05-02'
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
        student {
            github = 'system1cls'
            fullName = 'Фараносов Дмитрий'
            repo = 'https://github.com/system1cls/OOP'
        }
        student {
            github = 'Repeynik'
            fullName = 'Савушкина Алёна'
            repo = 'https://github.com/Repeynik/OOP'
        }
    }
}

course.assignments {
    assign ('Task_2_4_1', 'MCBivis')
    assign ('Task_2_4_1', 'nkrainov')
    assign ('Task_2_4_1', 'system1cls')
    assign ('Task_2_4_1', 'Repeynik')
    assign ('Task_1_1_2', 'MCBivis')
    assign ('Task_1_1_2', 'nkrainov')
    assign ('Task_1_1_2', 'system1cls')
    assign ('Task_1_1_2', 'Repeynik')
    assign ('Task_1_1_3', 'MCBivis')
    assign ('Task_1_1_3', 'nkrainov')
    assign ('Task_1_1_3', 'system1cls')
    assign ('Task_1_1_3', 'Repeynik')
}

course.milestones {
    milestone ('3 семестр', '2024-12-31')
    milestone ('4 семестр', '2025-05-31')
}

course.settings {
    bonusPoints = ['MCBivis': ['Task_1_1_2': 0.5], 'nkrainov': ['Task_1_1_3': 0.5]]
}