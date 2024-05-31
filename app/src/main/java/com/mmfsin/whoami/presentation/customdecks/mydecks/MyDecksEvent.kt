package com.mmfsin.whoami.presentation.customdecks.mydecks

import com.mmfsin.whoami.domain.models.MyDeck

sealed class MyDecksEvent {
    class MyDecks(val decks: List<MyDeck>) : MyDecksEvent()
    object FlowCompleted : MyDecksEvent()
    object SomethingWentWrong : MyDecksEvent()
}