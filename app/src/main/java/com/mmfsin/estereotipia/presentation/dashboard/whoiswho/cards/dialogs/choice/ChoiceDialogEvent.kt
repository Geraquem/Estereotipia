package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.choice

import com.mmfsin.estereotipia.domain.models.Card

sealed class ChoiceDialogEvent {
    class GetCard(val card: Card) : ChoiceDialogEvent()
    object SomethingWentWrong : ChoiceDialogEvent()
}