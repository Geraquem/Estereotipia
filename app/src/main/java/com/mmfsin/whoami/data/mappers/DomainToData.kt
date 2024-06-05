package com.mmfsin.whoami.data.mappers

import com.mmfsin.whoami.data.models.DeckDTO
import com.mmfsin.whoami.utils.toCardList
import java.util.UUID

fun createCustomDeckDTO(name: String, cards: List<String>) = DeckDTO(
    id = UUID.randomUUID().toString(),
    name = name,
    cards = cards.toCardList(),
    order = System.currentTimeMillis(),
    isCustom = true
)