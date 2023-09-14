package com.mmfsin.whoami.presentation.dashboard.cards

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck

sealed class CardsEvent {
    class GetCards(val cards: List<Card>) : CardsEvent()
    class UpdateCard(val cardId: String) : CardsEvent()
    object SomethingWentWrong : CardsEvent()
}