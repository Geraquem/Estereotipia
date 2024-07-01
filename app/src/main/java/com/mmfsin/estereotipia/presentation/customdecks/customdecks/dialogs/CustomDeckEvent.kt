package com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs

import com.mmfsin.estereotipia.domain.models.Deck

sealed class CustomDeckEvent {
    class GetDeck(val deck: Deck) : CustomDeckEvent()
    object EditedCompleted : CustomDeckEvent()
    object SomethingWentWrong : CustomDeckEvent()
}