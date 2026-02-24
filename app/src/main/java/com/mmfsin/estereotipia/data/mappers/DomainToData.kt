package com.mmfsin.estereotipia.data.mappers

import com.mmfsin.estereotipia.data.models.DeckDTO
import com.mmfsin.estereotipia.data.models.GameInfoDTO
import com.mmfsin.estereotipia.domain.models.GameInfo
import com.mmfsin.estereotipia.utils.GAME_INFO
import com.mmfsin.estereotipia.utils.toCardList
import java.util.UUID

fun createCustomDeckDTO(name: String, cards: List<String>) = DeckDTO().apply {
    id = UUID.randomUUID().toString()
    this.name = name
    this.cards = cards.toCardList()
    order = System.currentTimeMillis()
    isCustom = true
}

fun createGameInfo(info: GameInfo) = GameInfoDTO().apply {
    id = GAME_INFO
    this.instagramUser = info.instagramUser
    this.shopUrl = info.shopUrl
    this.physicalBox = info.physicalBox
}