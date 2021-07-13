package ru.sumin.jetpackstart.domain.usecase

import ru.sumin.jetpackstart.domain.entity.GameSettings
import ru.sumin.jetpackstart.domain.entity.Level
import ru.sumin.jetpackstart.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings = gameRepository.getGameSettings(level)
}
