package com.mmfsin.whoami.presentation.dashboard

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck

sealed class DashboardEvent {
    class GetActualDeck(val deck: Deck) : DashboardEvent()
    class GetCards(val cards: List<Card>) : DashboardEvent()
    object UpdateCards : DashboardEvent()
    object SomethingWentWrong : DashboardEvent()
}