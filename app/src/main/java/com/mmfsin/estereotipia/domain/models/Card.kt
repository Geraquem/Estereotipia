package com.mmfsin.estereotipia.domain.models

open class Card(
    val id: String,
    val image: String,
    val name: String,
    var discarded: Boolean = false,
    var suspicious: Boolean = false,
    var rivalCard: Boolean = false,
)
