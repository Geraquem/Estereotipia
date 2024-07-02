package com.mmfsin.estereotipia.presentation.customdecks.customdecks.interfaces

interface ICustomDeckListener {
    fun onCustomDeckClick(id: String)

    fun playWithCustomDeck(id: String)
    fun seeCards(id: String)

    fun editName(id: String)
    fun editCards(id: String)
    fun editCompleted()

    fun shareDeck(name: String, cards: String)
    fun confirmDeleteCustomDeck(id: String)
    fun deleteCustomDeck(id: String)
}