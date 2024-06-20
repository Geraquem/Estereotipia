package com.mmfsin.whoami.presentation.dashboard.questions.dialogs.interfaces

import com.mmfsin.whoami.domain.models.GameQuestion

interface INewQuestionListener {
    fun viewCards()
    fun answer(question: GameQuestion, answer: Boolean)
}