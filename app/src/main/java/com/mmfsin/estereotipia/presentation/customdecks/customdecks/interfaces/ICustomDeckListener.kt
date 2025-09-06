package com.mmfsin.estereotipia.presentation.customdecks.customdecks.interfaces

interface ICustomDeckListener {
    fun onCustomDeckClick(deckId: String)

    fun playWithCustomDeck(deckId: String)
    fun seeCards(deckId: String)

    fun editName(deckId: String)
    fun editCards(deckId: String)
    fun editCompleted()

    fun shareDeck(name: String, cards: String)
    fun confirmDeleteCustomDeck(deckId: String)
    fun deleteCustomDeck(deckId: String)
}