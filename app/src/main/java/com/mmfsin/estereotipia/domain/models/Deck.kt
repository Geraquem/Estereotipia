package com.mmfsin.estereotipia.domain.models

open class Deck(
    val id: String,
    val name: String,
    val cards: String,
    val numOfCards: Int,
    val isCustom: Boolean
)