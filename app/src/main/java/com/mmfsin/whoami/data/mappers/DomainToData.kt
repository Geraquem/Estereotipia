package com.mmfsin.whoami.data.mappers

import com.mmfsin.whoami.data.models.CustomDeckDTO
import com.mmfsin.whoami.domain.models.MyDeck
import java.util.*

fun MyDeck.toMyDeckDTO(defaultDeckImg: String, order: Long) = CustomDeckDTO(
    id = UUID.randomUUID().toString(),
    image = defaultDeckImg,
    name = this.name,
    cards = this.cards,
    order = order
)