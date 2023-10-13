package com.mmfsin.whoami.presentation.dashboard.viepager

import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.MyDeck

sealed class ViewPagerEvent {
    class SystemDeck(val deck: Deck) : ViewPagerEvent()
    class CustomDeck(val myDeck: MyDeck) : ViewPagerEvent()
    class SelectedCard(val selectedCardId: String) : ViewPagerEvent()
    object SomethingWentWrong : ViewPagerEvent()
}