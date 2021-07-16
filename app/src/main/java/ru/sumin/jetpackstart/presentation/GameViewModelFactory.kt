package ru.sumin.jetpackstart.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.sumin.jetpackstart.domain.entity.Level

class GameViewModelFactory(
    private val application: Application,
    private val level: Level
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (GameViewModel::class.java.isAssignableFrom(modelClass)) {
            return GameViewModel(application, level) as T
        }
        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}
