package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Card
import kotlinx.coroutines.flow.StateFlow

interface ICardsRepository {
    fun getCards(deckId: String): List<Card>?
    fun getCardById(id: String): Card?
    fun discardCard(id: String): Boolean?
    fun selectCard(id: String)
    fun observeFlow(): StateFlow<Pair<Boolean, String>>
}