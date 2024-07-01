package com.mmfsin.estereotipia.presentation.allcards.dialogs

import com.mmfsin.estereotipia.domain.models.Card

sealed class AllCardDialogEvent {
    class GetCard(val card: Card) : AllCardDialogEvent()
    object SomethingWentWrong : AllCardDialogEvent()
}