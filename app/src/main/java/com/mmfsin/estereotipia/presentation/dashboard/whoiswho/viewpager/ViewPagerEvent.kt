package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager

import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.domain.models.GameQuestion

sealed class ViewPagerEvent {
    class GetDeck(val deck: Deck) : ViewPagerEvent()
    class SelectedCard(val selectedCardId: String) : ViewPagerEvent()
    object SomethingWentWrong : ViewPagerEvent()
}