package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.discard

import com.mmfsin.whoami.domain.models.Card

sealed class DiscardDialogEvent {
    class GetCard(val card: Card) : DiscardDialogEvent()
    class DiscardCard(val discarded: Boolean?) : DiscardDialogEvent()
    object SomethingWentWrong : DiscardDialogEvent()
}