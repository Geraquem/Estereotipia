package com.mmfsin.estereotipia.presentation.dashboard.cards.interfaces

import com.mmfsin.estereotipia.domain.models.Card

interface ICardsListener {
    fun onCardClick(card: Card)

    fun markSuspicious(cardId: String)
    fun markDiscarded(cardId: String)

    fun makeChoice(cardId: String)
    fun choiceComplete(winner: Boolean, cardId: String)
}