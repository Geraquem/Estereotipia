package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.questions.dialogs.interfaces

import com.mmfsin.estereotipia.domain.models.GameQuestion

interface INewQuestionListener {
    fun viewCards()
    fun answer(question: GameQuestion, answer: Boolean)
}