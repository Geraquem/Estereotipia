package com.mmfsin.whoami.presentation.dashboard.questions

import com.mmfsin.whoami.domain.models.Question

sealed class QuestionsEvent {
    class GetQuestions(val questions: List<Question>): QuestionsEvent()
    object SomethingWentWrong : QuestionsEvent()
}