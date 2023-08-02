package com.mmfsin.whoami.presentation.dashboard.captain.dialog

import com.mmfsin.whoami.domain.models.Card

sealed class CaptainCardInfoEvent {
    class GetPeopleCard(val card: Card) : CaptainCardInfoEvent()
    object SomethingWentWrong : CaptainCardInfoEvent()
}