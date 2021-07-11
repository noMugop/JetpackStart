package ru.sumin.jetpackstart

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val answers: Set<Int>
) {

    val rightAnswer: Int
        get() = sum - visibleNumber

    companion object {

        private const val MIN_NUMBER = 1
        private const val COUNT_OF_ANSWERS = 6

        fun generateQuestion(maxValue: Int): Question {
            val sum = Random.nextInt(MIN_NUMBER, maxValue + 1)
            val visibleNumber = Random.nextInt(MIN_NUMBER, sum + 1)
            val options = HashSet<Int>()
            val rightAnswer = sum - visibleNumber
            options.add(rightAnswer)
            while (options.size < COUNT_OF_ANSWERS) {
                val from = max(0, rightAnswer - COUNT_OF_ANSWERS)
                val to = min(rightAnswer + COUNT_OF_ANSWERS, maxValue)
                options.add(Random.nextInt(from, to + 1))
            }
            return Question(sum, visibleNumber, options)
        }
    }
}
