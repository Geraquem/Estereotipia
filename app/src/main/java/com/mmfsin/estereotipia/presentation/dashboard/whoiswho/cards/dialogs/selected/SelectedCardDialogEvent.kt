package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.selected

import com.mmfsin.estereotipia.domain.models.Card

sealed class SelectedCardDialogEvent {
    class GetCard(val card: Card) : SelectedCardDialogEvent()
    object SomethingWentWrong : SelectedCardDialogEvent()
}