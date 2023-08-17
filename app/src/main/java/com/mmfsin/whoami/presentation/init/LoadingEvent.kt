package com.mmfsin.whoami.presentation.init

sealed class LoadingEvent {
    object Completed : LoadingEvent()
    object SomethingWentWrong : LoadingEvent()
}