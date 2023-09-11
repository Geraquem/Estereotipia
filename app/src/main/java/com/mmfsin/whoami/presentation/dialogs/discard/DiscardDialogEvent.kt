package com.mmfsin.whoami.presentation.dialogs.discard

import com.mmfsin.whoami.domain.models.Card

sealed class DiscardDialogEvent {
    class GetPeopleCard(val card: Card) : DiscardDialogEvent()
    class DiscardPeopleCard(val discarded: Boolean?) : DiscardDialogEvent()
    object SomethingWentWrong : DiscardDialogEvent()
}