package com.mmfsin.estereotipia.presentation.identities

import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.Identity

sealed class IdentitiesEvent {
    class GetIdentities(val identities: List<Identity>) : IdentitiesEvent()
    class GetThreeCards(val cards: List<Card>) : IdentitiesEvent()
    object SomethingWentWrong : IdentitiesEvent()
}