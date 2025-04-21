package com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases

import com.mmfsin.estereotipia.domain.models.GamePhrase

sealed class PhrasesEvent {
    class GetPhrases(val phrases: List<GamePhrase>) : PhrasesEvent()
    object SomethingWentWrong : PhrasesEvent()
}