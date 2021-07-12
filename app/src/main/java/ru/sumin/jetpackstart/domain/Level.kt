package ru.sumin.jetpackstart.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {

    EASY, NORMAL, HARD, TEST
}
