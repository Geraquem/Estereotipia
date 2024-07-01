package com.mmfsin.estereotipia.presentation.customdecks.create.dialog

sealed class DeckNameEvent {
    object CreatedCompleted : DeckNameEvent()
    object SomethingWentWrong : DeckNameEvent()
}