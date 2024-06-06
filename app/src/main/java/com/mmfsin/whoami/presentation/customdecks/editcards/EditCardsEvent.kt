package com.mmfsin.whoami.presentation.customdecks.editcards

import com.mmfsin.whoami.domain.models.Deck

sealed class EditCardsEvent {
    class GetDeck(val deck: Deck) : EditCardsEvent()
    object SomethingWentWrong : EditCardsEvent()
}