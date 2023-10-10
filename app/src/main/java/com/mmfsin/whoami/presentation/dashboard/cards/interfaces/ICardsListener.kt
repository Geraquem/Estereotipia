package com.mmfsin.whoami.presentation.dashboard.cards.interfaces

interface ICardsListener {
    fun onCardClick(cardId: String)

    fun makeChoice(cardId: String)
    fun choiceComplete(winner: Boolean)
}