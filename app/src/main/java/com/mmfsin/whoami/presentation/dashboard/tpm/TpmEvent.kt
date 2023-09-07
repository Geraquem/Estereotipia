package com.mmfsin.whoami.presentation.dashboard.tpm

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck

sealed class TpmEvent {
    class GetActualDeck(val deck: Deck) : TpmEvent()
    class GetCards(val cards: List<Card>) : TpmEvent()
    class UpdateCard(val cardId: String) : TpmEvent()
    object SomethingWentWrong : TpmEvent()
}