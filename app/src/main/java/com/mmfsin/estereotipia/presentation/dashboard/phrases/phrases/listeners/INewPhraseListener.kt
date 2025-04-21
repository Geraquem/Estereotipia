package com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.listeners

import com.mmfsin.estereotipia.domain.models.GamePhrase

interface INewPhraseListener {
    fun answerPhrase(phrase: GamePhrase, answer: String)
}