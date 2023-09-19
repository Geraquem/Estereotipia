package com.mmfsin.whoami.presentation.menu.decks

import com.mmfsin.whoami.domain.models.Deck

sealed class DecksEvent {
    class GetDecks(val decks: List<Deck>) : DecksEvent()
    object SomethingWentWrong : DecksEvent()
}