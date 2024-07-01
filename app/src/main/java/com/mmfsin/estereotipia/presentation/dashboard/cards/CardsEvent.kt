package com.mmfsin.estereotipia.presentation.dashboard.cards

import com.mmfsin.estereotipia.domain.models.Card

sealed class CardsEvent {
    class GetCards(val cards: List<Card>) : CardsEvent()
    class UpdateCard(val cardId: String) : CardsEvent()
    object SomethingWentWrong : CardsEvent()
}