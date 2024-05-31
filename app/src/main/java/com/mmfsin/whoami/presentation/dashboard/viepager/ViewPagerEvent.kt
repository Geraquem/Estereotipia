package com.mmfsin.whoami.presentation.dashboard.viepager

import com.mmfsin.whoami.domain.models.Deck

sealed class ViewPagerEvent {
    class GetDeck(val deck: Deck) : ViewPagerEvent()
    class SelectedCard(val selectedCardId: String) : ViewPagerEvent()
    object SomethingWentWrong : ViewPagerEvent()
}