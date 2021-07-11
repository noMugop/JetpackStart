package ru.sumin.jetpackstart

data class GameSettings(
    val maxValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) {

    companion object {

        fun getGameSettingsByLevel(level: Level): GameSettings {
            return when (level) {
                Level.EASY   -> GameSettings(
                    10,
                    10,
                    60,
                    60
                )
                Level.NORMAL -> GameSettings(
                    20,
                    20,
                    75,
                    50
                )
                Level.HARD   -> GameSettings(
                    30,
                    30,
                    90,
                    20
                )
                Level.TEST   -> GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }
        }
    }
}
