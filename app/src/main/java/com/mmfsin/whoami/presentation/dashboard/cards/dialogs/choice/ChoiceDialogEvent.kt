package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.choice

import com.mmfsin.whoami.domain.models.Card

sealed class ChoiceDialogEvent {
    class GetCard(val card: Card) : ChoiceDialogEvent()
    object SomethingWentWrong : ChoiceDialogEvent()
}