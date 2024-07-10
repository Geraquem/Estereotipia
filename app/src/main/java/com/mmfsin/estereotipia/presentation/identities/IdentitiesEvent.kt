package com.mmfsin.estereotipia.presentation.identities

import com.mmfsin.estereotipia.domain.models.Card

sealed class IdentitiesEvent {
    class GetThreeCards(val cards: List<Card>) : IdentitiesEvent()
    object SomethingWentWrong : IdentitiesEvent()
}