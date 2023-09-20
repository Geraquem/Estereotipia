package com.mmfsin.whoami.presentation.menu

sealed class MenuEvent {
    object Completed : MenuEvent()
    object SomethingWentWrong : MenuEvent()
}