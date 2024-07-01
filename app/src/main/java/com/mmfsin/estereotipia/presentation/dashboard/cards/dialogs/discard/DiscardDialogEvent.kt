package com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.discard

import com.mmfsin.estereotipia.domain.models.Card

sealed class DiscardDialogEvent {
    class GetCard(val card: Card) : DiscardDialogEvent()
    class DiscardCard(val discarded: Boolean?) : DiscardDialogEvent()
    object SomethingWentWrong : DiscardDialogEvent()
}