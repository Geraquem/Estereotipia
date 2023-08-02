package com.mmfsin.whoami.presentation.dashboard.people.dialog

import com.mmfsin.whoami.domain.models.Card

sealed class PeopleCardInfoEvent {
    class GetPeopleCard(val card: Card) : PeopleCardInfoEvent()
    object DiscardPeopleCard : PeopleCardInfoEvent()
    object SomethingWentWrong : PeopleCardInfoEvent()
}