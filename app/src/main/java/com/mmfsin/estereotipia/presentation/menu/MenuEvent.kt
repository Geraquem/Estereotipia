package com.mmfsin.estereotipia.presentation.menu

import com.mmfsin.estereotipia.domain.models.Card

sealed class MenuEvent {
    object Completed : MenuEvent()
    class MenuCards(val cards: List<Card>) : MenuEvent()
    class FirstTime(val isFirstTime: Boolean) : MenuEvent()
    object SomethingWentWrong : MenuEvent()
}