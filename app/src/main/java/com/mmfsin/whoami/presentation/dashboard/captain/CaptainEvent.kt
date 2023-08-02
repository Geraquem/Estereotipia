package com.mmfsin.whoami.presentation.dashboard.captain

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck

sealed class CaptainEvent {
    class GetActualDeck(val deck: Deck) : CaptainEvent()
    class GetCards(val cards: List<Card>) : CaptainEvent()
    object SomethingWentWrong : CaptainEvent()
}