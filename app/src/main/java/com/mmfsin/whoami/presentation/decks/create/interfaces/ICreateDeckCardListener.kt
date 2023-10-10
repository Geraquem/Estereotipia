package com.mmfsin.whoami.presentation.decks.create.interfaces

interface ICreateDeckCardListener {
    fun onCardClick(id: String)
    fun onCheckClick(position: Int, selected: Boolean, id: String)
    fun flowCompleted()
}