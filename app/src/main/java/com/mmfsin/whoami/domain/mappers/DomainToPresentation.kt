package com.mmfsin.whoami.domain.mappers

import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.domain.models.CreateDeckCard

fun Card.toCreateDeckCard() = CreateDeckCard(card = this, selected = false)

fun List<Card>.toCreateDeckCardList() = this.map { it.toCreateDeckCard() }