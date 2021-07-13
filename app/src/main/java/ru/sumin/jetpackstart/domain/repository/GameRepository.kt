package ru.sumin.jetpackstart.domain.repository

import ru.sumin.jetpackstart.domain.entity.GameSettings
import ru.sumin.jetpackstart.domain.entity.Level
import ru.sumin.jetpackstart.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxValue: Int,
        minSumValue: Int,
        minAnswerValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}
