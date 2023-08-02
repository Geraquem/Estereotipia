package com.mmfsin.whoami.presentation.dashboard.people.interfaces

interface IPeopleCardListener {
    fun onCardClick(cardId: String)
    fun onDiscardClick(cardId: String)
}