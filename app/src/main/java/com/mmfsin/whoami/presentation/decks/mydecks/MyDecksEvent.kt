package com.mmfsin.whoami.presentation.decks.mydecks

import com.mmfsin.whoami.domain.models.MyDeck

sealed class MyDecksEvent {
    class MyDecks(val decks: List<MyDeck>) : MyDecksEvent()
    object SomethingWentWrong : MyDecksEvent()
}