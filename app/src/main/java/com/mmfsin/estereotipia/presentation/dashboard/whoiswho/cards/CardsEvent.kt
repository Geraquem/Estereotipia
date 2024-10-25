package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards

import com.mmfsin.estereotipia.domain.models.Card

sealed class CardsEvent {
    class GetCards(val cards: List<Card>) : CardsEvent()
    object SomethingWentWrong : CardsEvent()
}