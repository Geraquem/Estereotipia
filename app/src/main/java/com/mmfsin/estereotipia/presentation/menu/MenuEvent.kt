package com.mmfsin.estereotipia.presentation.menu

import com.mmfsin.estereotipia.domain.models.Card

sealed class MenuEvent {
    data object Completed : MenuEvent()
    class MenuCards(val card: Card?) : MenuEvent()
    data object SomethingWentWrong : MenuEvent()
}