package com.mmfsin.estereotipia.presentation.dashboard.cards.interfaces

interface ICardsListener {
    fun onCardClick(cardId: String)

    fun markSuspicious(cardId: String)

    fun makeChoice(cardId: String)
    fun choiceComplete(winner: Boolean, cardId: String)
}