package com.mmfsin.whoami.presentation.dashboard.captain

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.presentation.dashboard.captain.dialog.CaptainCardInfoEvent

sealed class CaptainEvent {
    class GetActualDeck(val deck: Deck) : CaptainEvent()
    class GetCards(val cards: List<Card>) : CaptainEvent()
    class UpdateCard(val cardId: String) : CaptainEvent()
    object SomethingWentWrong : CaptainEvent()
}