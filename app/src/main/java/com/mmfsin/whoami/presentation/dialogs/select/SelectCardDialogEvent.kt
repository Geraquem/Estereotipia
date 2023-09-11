package com.mmfsin.whoami.presentation.dialogs.select

import com.mmfsin.whoami.domain.models.Card

sealed class SelectCardDialogEvent {
    class GetPeopleCardDialog(val card: Card) : SelectCardDialogEvent()
    object SomethingWentWrong : SelectCardDialogEvent()
}