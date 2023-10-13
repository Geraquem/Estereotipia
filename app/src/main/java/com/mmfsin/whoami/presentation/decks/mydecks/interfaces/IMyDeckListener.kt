package com.mmfsin.whoami.presentation.decks.mydecks.interfaces

interface IMyDeckListener {
    fun onMyDeckClick(id: String)

    fun editName(id: String)
    fun editCards(id: String)
    fun editCompleted()

    fun confirmDeleteMyDeck(id: String)
    fun deleteMyDeck(id: String)
}