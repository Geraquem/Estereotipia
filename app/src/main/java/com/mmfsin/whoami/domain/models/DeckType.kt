package com.mmfsin.whoami.domain.models

enum class DeckType(val type: Int) {
    SYSTEM(0),
    CUSTOM(1)
}

fun Int.getDeckTypeByInt(): DeckType = when (this) {
    0 -> DeckType.SYSTEM
    else -> DeckType.CUSTOM
}