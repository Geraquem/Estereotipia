package com.mmfsin.whoami.presentation.allcards

import com.mmfsin.whoami.domain.models.Card

sealed class AllCardsEvent {
    class GetCards(val cards: List<Card>) : AllCardsEvent()
    object SomethingWentWrong : AllCardsEvent()
}