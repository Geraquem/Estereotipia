package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Card
import kotlinx.coroutines.flow.StateFlow

interface ICardsRepository {
    fun getCardsByDeckId(deckId: String): List<Card>?
    fun getCardsByCustomDeckId(deckId: String): List<Card>?

    fun getAllCards(): List<Card>?
    fun getCardById(id: String): Card?
    fun discardCard(id: String): Boolean?
    fun selectCard(id: String)
    fun observeFlow(): StateFlow<Pair<Boolean, String>>
}