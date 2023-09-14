package com.mmfsin.whoami.presentation.dashboard.viepager

sealed class ViewPagerEvent {
    class SelectedCard(val cardId: String): ViewPagerEvent()
    object SomethingWentWrong : ViewPagerEvent()
}