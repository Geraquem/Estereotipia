package com.mmfsin.estereotipia.domain.models

open class Instruction(
    val order: Int,
    val group: Int,
    val icon: Int,
    val title: String,
    val text: Int? = null,
    val layout: Int? = null,
    var textOpened: Boolean = false,
    var background: Int
)
