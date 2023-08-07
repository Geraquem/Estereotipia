package com.mmfsin.whoami.presentation.init

import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.domain.models.Question

sealed class LoadingEvent {
    class GetVersion(val result: String) : LoadingEvent()
    class GetDecks(val result: List<Deck>) : LoadingEvent()
    class GetQuestions(val result: List<Question>) : LoadingEvent()
    object SomethingWentWrong : LoadingEvent()
}