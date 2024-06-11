package com.mmfsin.whoami.presentation.menu.dialogs

sealed class SharedDeckEvent {
    object AddedCompleted : SharedDeckEvent()
    object SomethingWentWrong : SharedDeckEvent()
}