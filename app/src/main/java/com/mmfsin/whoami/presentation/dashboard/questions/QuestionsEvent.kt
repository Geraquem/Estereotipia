package com.mmfsin.whoami.presentation.dashboard.questions

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.Deck

sealed class QuestionsEvent {

    object SomethingWentWrong : QuestionsEvent()
}