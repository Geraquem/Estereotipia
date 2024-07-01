package com.mmfsin.estereotipia.presentation.customdecks.editcards.interfaces

interface IEditCardsListener {
    fun onCardClick(id: String)
    fun onCheckClick(position: Int, selected: Boolean, id: String)
    fun flowCompleted()
}