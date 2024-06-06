package com.mmfsin.whoami.presentation.customdecks.editcards

import com.mmfsin.whoami.domain.models.CreateDeckCard
import com.mmfsin.whoami.domain.models.Deck

sealed class EditCardsEvent {
    class AllCards(val cards: List<CreateDeckCard>) : EditCardsEvent()
    class GetDeck(val deck: Deck) : EditCardsEvent()
    object CardsEditedCompleted : EditCardsEvent()
    object SomethingWentWrong : EditCardsEvent()
}