package com.mmfsin.estereotipia.presentation.customdecks.customdecks

import com.mmfsin.estereotipia.domain.models.Deck

sealed class CustomDecksEvent {
    class CustomDecks(val decks: List<Deck>) : CustomDecksEvent()
    object FlowCompleted : CustomDecksEvent()
    object SomethingWentWrong : CustomDecksEvent()
}