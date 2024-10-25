package com.mmfsin.estereotipia.presentation.menu.decks

import com.mmfsin.estereotipia.domain.models.AllDecks

sealed class DecksEvent {
    class GetDecks(val decks: AllDecks) : DecksEvent()
    object SomethingWentWrong : DecksEvent()
}