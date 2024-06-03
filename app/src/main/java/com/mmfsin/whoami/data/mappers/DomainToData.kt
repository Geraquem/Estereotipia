package com.mmfsin.whoami.data.mappers

import com.mmfsin.whoami.data.models.DeckDTO
import java.util.UUID

fun createCustomDeckDTO(name: String, cards: List<String>) = DeckDTO(
    id = UUID.randomUUID().toString(),
    name = name,
    cards = cards.toString(),
    order = System.currentTimeMillis(),
    isCustom = true
)