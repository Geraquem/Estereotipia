package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs

import com.mmfsin.estereotipia.domain.models.Card

sealed class IdentityCardDialogEvent {
    class GetCard(val card: Card) : IdentityCardDialogEvent()
    object SomethingWentWrong : IdentityCardDialogEvent()
}