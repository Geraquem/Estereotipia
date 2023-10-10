package com.mmfsin.whoami.presentation.decks.create

import com.mmfsin.whoami.domain.models.CreateDeckCard

sealed class CreateDeckEvent {
    class AllCards(val cards: List<CreateDeckCard>) : CreateDeckEvent()
    object SomethingWentWrong : CreateDeckEvent()
}