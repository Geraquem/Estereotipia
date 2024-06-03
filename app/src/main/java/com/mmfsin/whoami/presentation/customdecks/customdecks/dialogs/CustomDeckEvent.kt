package com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs

import com.mmfsin.whoami.domain.models.Deck

sealed class CustomDeckEvent {
    class GetDeck(val deck: Deck) : CustomDeckEvent()
    object EditedCompleted : CustomDeckEvent()
    object SomethingWentWrong : CustomDeckEvent()
}