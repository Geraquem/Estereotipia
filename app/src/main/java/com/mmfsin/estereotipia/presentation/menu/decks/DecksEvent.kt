package com.mmfsin.estereotipia.presentation.menu.decks

import com.mmfsin.estereotipia.domain.models.Deck

sealed class DecksEvent {
    class GetDecks(val decks: List<Deck>) : DecksEvent()
    object SomethingWentWrong : DecksEvent()
}