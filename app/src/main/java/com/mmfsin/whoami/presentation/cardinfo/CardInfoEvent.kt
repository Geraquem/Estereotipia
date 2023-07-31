package com.mmfsin.whoami.presentation.cardinfo

import com.mmfsin.whoami.domain.models.Card

sealed class CardInfoEvent {
    class GetCard(val card: Card) : CardInfoEvent()
    object SomethingWentWrong : CardInfoEvent()
}