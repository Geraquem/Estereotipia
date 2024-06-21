package com.mmfsin.whoami.presentation.dashboard.questions

import com.mmfsin.whoami.domain.models.GameQuestion

sealed class QuestionsEvent {
    class GetQuestions(val questions: List<GameQuestion>) : QuestionsEvent()
    object SomethingWentWrong : QuestionsEvent()
}