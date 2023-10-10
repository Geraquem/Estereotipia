package com.mmfsin.whoami.presentation.decks.mydecks

sealed class MyDecksEvent {
    object Completed : MyDecksEvent()
    object SomethingWentWrong : MyDecksEvent()
}