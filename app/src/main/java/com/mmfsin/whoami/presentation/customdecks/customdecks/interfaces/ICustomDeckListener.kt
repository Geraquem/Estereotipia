package com.mmfsin.whoami.presentation.customdecks.customdecks.interfaces

interface ICustomDeckListener {
    fun onCustomDeckClick(id: String)

    fun playWithCustomDeck(id: String)

    fun editName(id: String)
    fun editCards(id: String)
    fun editCompleted()

    fun confirmDeleteMyDeck(id: String)
    fun deleteMyDeck(id: String)
}