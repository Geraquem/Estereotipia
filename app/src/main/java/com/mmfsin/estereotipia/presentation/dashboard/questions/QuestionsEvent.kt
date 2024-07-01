package com.mmfsin.estereotipia.presentation.dashboard.questions

import com.mmfsin.estereotipia.domain.models.GameQuestion

sealed class QuestionsEvent {
    class GetQuestions(val questions: List<GameQuestion>) : QuestionsEvent()
    object SomethingWentWrong : QuestionsEvent()
}