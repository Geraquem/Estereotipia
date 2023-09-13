package com.mmfsin.whoami.presentation.dashboard.cards

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck

sealed class CardsEvent {
    class GetActualDeck(val deck: Deck) : CardsEvent()
    class GetCards(val cards: List<Card>) : CardsEvent()
    class RandomSelectedCard(val cardId: String): CardsEvent()
    class UpdateCard(val cardId: String) : CardsEvent()
    object SomethingWentWrong : CardsEvent()
}