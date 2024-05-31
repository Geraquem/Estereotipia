package com.mmfsin.whoami.presentation.customdecks.create.dialog

sealed class DeckNameEvent {
    object CreatedCompleted : DeckNameEvent()
    object SomethingWentWrong : DeckNameEvent()
}