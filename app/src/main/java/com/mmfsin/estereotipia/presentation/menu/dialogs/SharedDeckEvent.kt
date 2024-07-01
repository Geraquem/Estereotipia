package com.mmfsin.estereotipia.presentation.menu.dialogs

sealed class SharedDeckEvent {
    object AddedCompleted : SharedDeckEvent()
    object SomethingWentWrong : SharedDeckEvent()
}