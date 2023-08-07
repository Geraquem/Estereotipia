package com.mmfsin.whoami.presentation.decks

import com.mmfsin.whoami.domain.models.Deck

sealed class DecksEvent {
    class TwoPlayerMode(val activated: Boolean) : DecksEvent()
    class GetDecks(val result: List<Deck>) : DecksEvent()
    object SomethingWentWrong : DecksEvent()
}