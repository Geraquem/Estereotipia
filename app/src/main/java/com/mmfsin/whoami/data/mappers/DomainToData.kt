package com.mmfsin.whoami.data.mappers

import com.mmfsin.whoami.data.models.MyDeckDTO
import com.mmfsin.whoami.domain.models.MyDeck
import java.util.*

fun MyDeck.toMyDeckDTO(defaultDeckImg: String, order: Long) = MyDeckDTO(
    id = UUID.randomUUID().toString(),
    image = defaultDeckImg,
    name = this.name,
    cards = this.cards,
    order = order
)