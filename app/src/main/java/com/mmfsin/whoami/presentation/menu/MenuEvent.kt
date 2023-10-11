package com.mmfsin.whoami.presentation.menu

import com.mmfsin.whoami.domain.models.Card

sealed class MenuEvent {
    object Completed : MenuEvent()
    class MenuCards(val cards: List<Card>) : MenuEvent()
    object SomethingWentWrong : MenuEvent()
}