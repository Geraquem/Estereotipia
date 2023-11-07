package com.mmfsin.whoami.presentation.menu.listener

interface IMenuListener {
    fun startGame(deckId: String)
    fun openMyDecks()
    fun openCreateDeck()
    fun openAllCards()
}