package ru.sumin.jetpackstart

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val _leftFormattedTime = MutableLiveData<String>()
    val leftFormattedTime: LiveData<String>
        get() = _leftFormattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    val enoughPercentage = Transformations.map(percentOfRightAnswers) {
        it > gameSettings.minPercentOfRightAnswers
    }

    private var timer: CountDownTimer? = null
    private var countOfRightAnswers = 0
    private var countOfWrongAnswers = 0

    fun startGame(level: Level) {
        setupGameSettings(level)
        startTimer()
        generateQuestion()
    }

    fun chooseAnswer(answer: Int) {
        if (gameResult.value != null) {
            return
        }
        checkAnswer(answer)
        getPercentOfRightAnswers()
        generateQuestion()
    }

    private fun setupGameSettings(level: Level) {
        this.level = level
        gameSettings = GameSettings.getGameSettingsByLevel(level)
    }

    private fun checkAnswer(answer: Int) {
        val rightAnswer = question.value
        if (answer == rightAnswer?.rightAnswer) {
            countOfRightAnswers++
        } else {
            countOfWrongAnswers++
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _leftFormattedTime.value = getFormattedLeftTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = Question.generateQuestion(gameSettings.maxValue)
    }

    private fun finishGame() {
        _leftFormattedTime.value = getFormattedLeftTime(0)
        _gameResult.value = getGameResult()
    }

    private fun getGameResult(): GameResult {
        val percentOfRightAnswers = getPercentOfRightAnswers()
        val enoughPercentage = percentOfRightAnswers > gameSettings.minPercentOfRightAnswers
        val enoughRightAnswers = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        val winner = enoughPercentage && enoughRightAnswers
        val countOfQuestions = countOfRightAnswers + countOfWrongAnswers
        return GameResult(winner, countOfRightAnswers, countOfQuestions, level)
    }

    private fun getPercentOfRightAnswers(): Int {
        val countOfQuestions = countOfRightAnswers + countOfWrongAnswers
        val percentOfRightAnswers = if (countOfQuestions > 0) {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        } else {
            0
        }
        _percentOfRightAnswers.value = percentOfRightAnswers
        return percentOfRightAnswers
    }

    private fun getFormattedLeftTime(millisUntilFinished: Long): String {
        val seconds = (millisUntilFinished / MILLIS_IN_SECONDS).toInt()
        val minutes = seconds / SECONDS_IN_MINUTE
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }
}
