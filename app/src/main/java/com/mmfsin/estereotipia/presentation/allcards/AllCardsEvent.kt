package com.mmfsin.estereotipia.presentation.allcards

import com.mmfsin.estereotipia.domain.models.Card

sealed class AllCardsEvent {
    class GetCards(val cards: List<Card>) : AllCardsEvent()
    object SomethingWentWrong : AllCardsEvent()
}