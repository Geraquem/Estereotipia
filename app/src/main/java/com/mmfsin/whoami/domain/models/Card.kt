package com.mmfsin.whoami.domain.models

open class Card(
    val id: String,
    val deckId: String,
    val image: String,
    val name: String,
    var selected: Boolean,
    var discarded: Boolean
)
