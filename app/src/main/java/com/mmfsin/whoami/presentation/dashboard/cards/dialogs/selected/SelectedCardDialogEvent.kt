package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.selected

import com.mmfsin.whoami.domain.models.Card

sealed class SelectedCardDialogEvent {
    class GetCard(val card: Card) : SelectedCardDialogEvent()
    object SomethingWentWrong : SelectedCardDialogEvent()
}