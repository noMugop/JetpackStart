package ru.sumin.jetpackstart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {

    EASY, NORMAL, HARD, TEST
}
