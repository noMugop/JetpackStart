package ru.sumin.jetpackstart.domain.usecase

import ru.sumin.jetpackstart.domain.entity.Question
import ru.sumin.jetpackstart.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(maxValue: Int): Question = gameRepository.generateQuestion(
        maxValue,
        MIN_SUM_VALUE,
        MIN_VISIBLE_NUMBER_VALUE,
        COUNT_OF_OPTIONS
    )

    companion object {

        private const val MIN_SUM_VALUE = 2
        private const val MIN_VISIBLE_NUMBER_VALUE = 1
        private const val COUNT_OF_OPTIONS = 6
    }
}
