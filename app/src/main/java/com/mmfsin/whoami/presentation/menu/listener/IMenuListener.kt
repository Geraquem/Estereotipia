package com.mmfsin.whoami.presentation.menu.listener

interface IMenuListener {
    fun startGame(deckId: String)
    fun openInstructions()
    fun openMyDecks()
    fun openCreateDeck()
    fun openAllCards()
}