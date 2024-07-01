package com.mmfsin.estereotipia.presentation.customdecks.create

import com.mmfsin.estereotipia.domain.models.CreateDeckCard

sealed class CreateDeckEvent {
    class AllCards(val cards: List<CreateDeckCard>) : CreateDeckEvent()
    object SomethingWentWrong : CreateDeckEvent()
}