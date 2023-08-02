package com.mmfsin.whoami.presentation.dashboard.people

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck

sealed class PeopleEvent {
    class GetActualDeck(val deck: Deck) : PeopleEvent()
    class GetCards(val cards: List<Card>) : PeopleEvent()
    class UpdateCard(val cardId: String) : PeopleEvent()
    object SomethingWentWrong : PeopleEvent()
}