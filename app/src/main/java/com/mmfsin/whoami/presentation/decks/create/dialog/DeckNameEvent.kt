package com.mmfsin.whoami.presentation.decks.create.dialog

sealed class DeckNameEvent {
    object CreatedCompleted : DeckNameEvent()
    object SomethingWentWrong : DeckNameEvent()
}