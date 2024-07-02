package com.mmfsin.estereotipia.presentation.customdecks.seecards

import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.Deck

sealed class SeeCardsEvent {
    class GetDeck(val deck: Deck) : SeeCardsEvent()
    class DeckCards(val cards: List<Card>) : SeeCardsEvent()
    object SomethingWentWrong : SeeCardsEvent()
}