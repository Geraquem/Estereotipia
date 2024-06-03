package com.mmfsin.whoami.presentation.customdecks.customdecks

import com.mmfsin.whoami.domain.models.Deck

sealed class CustomDecksEvent {
    class CustomDecks(val decks: List<Deck>) : CustomDecksEvent()
    object FlowCompleted : CustomDecksEvent()
    object SomethingWentWrong : CustomDecksEvent()
}