package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager

import com.mmfsin.estereotipia.domain.models.Deck

sealed class ViewPagerWWEvent {
    class GetDeck(val deck: Deck) : ViewPagerWWEvent()
    class SelectedCard(val selectedCardId: String) : ViewPagerWWEvent()
    object SomethingWentWrong : ViewPagerWWEvent()
}