course.tasks {
    task('Task_Test') {
        title = 'Консольный блэкджек'
        maxScore = 1
        softDeadline = '2024-09-17'
        hardDeadline = '2024-09-24'
    }
}

course.groups {
    group('23216') {
        student {
            github = 'MCBivis'
            fullName = 'Кулешов Артемий'
            repo = 'https://github.com/MCBivis/OOP'
        }
    }
}

course.assignments {
    assign ('Task_Test', 'MCBivis')
}

course.milestones {
    milestone ('3 семестр', '2024-12-31')
    milestone ('4 семестр', '2025-05-31')
}

course.settings {
    bonusPoints = ['MCBivis': ['Task_Test': 0.5]]
}