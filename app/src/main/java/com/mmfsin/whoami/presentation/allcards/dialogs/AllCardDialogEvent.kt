package com.mmfsin.whoami.presentation.allcards.dialogs

import com.mmfsin.whoami.domain.models.Card

sealed class AllCardDialogEvent {
    class GetCard(val card: Card) : AllCardDialogEvent()
    object SomethingWentWrong : AllCardDialogEvent()
}