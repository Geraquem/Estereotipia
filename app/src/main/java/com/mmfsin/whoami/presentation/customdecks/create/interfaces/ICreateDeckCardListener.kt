package com.mmfsin.whoami.presentation.customdecks.create.interfaces

interface ICreateDeckCardListener {
    fun onCardClick(id: String)
    fun onCheckClick(position: Int, selected: Boolean, id: String)
    fun flowCompleted()
}