package com.mmfsin.estereotipia.presentation.customdecks.shared

sealed class SharedDeckEvent {
    object AddedCompleted : SharedDeckEvent()
    object SomethingWentWrong : SharedDeckEvent()
}