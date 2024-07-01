package com.mmfsin.estereotipia.presentation.customdecks.editcards

import com.mmfsin.estereotipia.domain.models.CreateDeckCard
import com.mmfsin.estereotipia.domain.models.Deck

sealed class EditCardsEvent {
    class AllCards(val cards: List<CreateDeckCard>) : EditCardsEvent()
    class GetDeck(val deck: Deck) : EditCardsEvent()
    object CardsEditedCompleted : EditCardsEvent()
    object SomethingWentWrong : EditCardsEvent()
}