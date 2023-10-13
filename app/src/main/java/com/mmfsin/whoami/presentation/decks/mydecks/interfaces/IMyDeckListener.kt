package com.mmfsin.whoami.presentation.decks.mydecks.interfaces

interface IMyDeckListener {
    fun onMyDeckClick(id: String)

    fun confirmDeleteMyDeck(id: String)
    fun deleteMyDeck(id: String)
}