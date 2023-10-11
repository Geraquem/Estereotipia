package com.mmfsin.whoami.domain.models

open class Instruction(
    val order: Int,
    val title: String,
    val text: Int? = null,
    val layout: Int? = null
)
