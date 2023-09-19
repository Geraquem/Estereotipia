package com.mmfsin.whoami.presentation.menu

sealed class MenuEvent {
    object Completed : MenuEvent()
//    class GetDecks(val result: List<Deck>) : MenuEvent()
    object SomethingWentWrong : MenuEvent()
}