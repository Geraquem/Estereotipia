package com.mmfsin.estereotipia.presentation.menu

import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.domain.models.GameInfo

sealed class MenuEvent {
    data object Completed : MenuEvent()
    class MenuCards(val card: Card?) : MenuEvent()
    class GetGameInfo(val info: GameInfo?) : MenuEvent()
    data object SomethingWentWrong : MenuEvent()
}