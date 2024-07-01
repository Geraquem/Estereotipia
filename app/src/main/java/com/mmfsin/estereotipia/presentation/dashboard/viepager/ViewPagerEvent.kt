package com.mmfsin.estereotipia.presentation.dashboard.viepager

import com.mmfsin.estereotipia.domain.models.Deck

sealed class ViewPagerEvent {
    class GetDeck(val deck: Deck) : ViewPagerEvent()
    class SelectedCard(val selectedCardId: String) : ViewPagerEvent()
    object SomethingWentWrong : ViewPagerEvent()
}