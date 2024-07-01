package com.mmfsin.estereotipia.data.mappers

import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.utils.toCardList
import java.util.UUID

fun createCustomDeckDTO(name: String, cards: List<String>) = DeckDTO(
    id = UUID.randomUUID().toString(),
    name = name,
    cards = cards.toCardList(),
    order = System.currentTimeMillis(),
    isCustom = true
)