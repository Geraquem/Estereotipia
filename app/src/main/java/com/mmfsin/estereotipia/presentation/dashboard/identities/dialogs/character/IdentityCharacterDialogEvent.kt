package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.character

import com.mmfsin.estereotipia.domain.models.Card

sealed class IdentityCharacterDialogEvent {
    class GetCharacter(val card: Card) : IdentityCharacterDialogEvent()
    object SomethingWentWrong : IdentityCharacterDialogEvent()
}