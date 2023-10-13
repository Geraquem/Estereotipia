package com.mmfsin.whoami.presentation.decks.mydecks.dialogs

import com.mmfsin.whoami.domain.models.MyDeck

sealed class MyDeckEvent {
    class GetDeck(val deck: MyDeck) : MyDeckEvent()
    object EditedCompleted : MyDeckEvent()
    object SomethingWentWrong : MyDeckEvent()
}